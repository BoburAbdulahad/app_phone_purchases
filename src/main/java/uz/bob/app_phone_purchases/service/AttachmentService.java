package uz.bob.app_phone_purchases.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.bob.app_phone_purchases.entity.Attachment;
import uz.bob.app_phone_purchases.entity.AttachmentContent;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.repository.AttachmentContentRepository;
import uz.bob.app_phone_purchases.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public List<Attachment> getAll(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Attachment> attachmentPage = attachmentRepository.findAll(pageable);
        return attachmentPage.getContent();
    }

    public Attachment getOneById(Long id){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        return optionalAttachment.orElse(null);
    }

    @SneakyThrows
    public ApiResponse add(MultipartHttpServletRequest request){
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file==null)
            return new ApiResponse("File is null!",false);
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        String contentType = file.getContentType();
        Attachment attachment=new Attachment();
        attachment.setName(originalFilename);
        attachment.setSize(size);
        attachment.setContentType(contentType);

        Attachment savedAttachment = attachmentRepository.save(attachment);

        AttachmentContent attachmentContent=new AttachmentContent();
        attachmentContent.setMainContent(file.getBytes());
        attachmentContent.setAttachment(savedAttachment);
        attachmentContentRepository.save(attachmentContent);
        return new ApiResponse("File saved",true);

    }

    @SneakyThrows
    public ApiResponse edit(Long id, MultipartHttpServletRequest request){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (!optionalAttachment.isPresent()) {
            return new ApiResponse("Attachment not found",false);
        }
        Attachment editingAttachment = optionalAttachment.get();
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file!=null) {
            editingAttachment.setName(file.getOriginalFilename());
            editingAttachment.setSize(file.getSize());
            editingAttachment.setContentType(file.getContentType());
            Attachment savedAttachment = attachmentRepository.save(editingAttachment);

            AttachmentContent editingAttachmentContent = attachmentContentRepository.findByAttachmentId(editingAttachment.getId());
            editingAttachmentContent.setMainContent(file.getBytes());

            editingAttachmentContent.setAttachment(savedAttachment);
            attachmentContentRepository.save(editingAttachmentContent);
            return new ApiResponse("Attachment full edited",true,savedAttachment);
        }
        return new ApiResponse("File is null,sorry",false);
    }

    public boolean delete(Long id){
        try {
            Attachment attachment = attachmentRepository.getReferenceById(id);
            attachmentContentRepository.deleteById(attachment.getId());
            attachmentRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @SneakyThrows
    public ApiResponse getPhoto(Long id, HttpServletResponse response) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findById(attachment.getId());
            if (optionalAttachmentContent.isPresent()) {
                AttachmentContent attachmentContent = optionalAttachmentContent.get();
                response.setHeader("Content-Disposition","attachment;filename=\""+attachment.getName()+"\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getMainContent(),response.getOutputStream());
                return new ApiResponse("=====Photo=====",true,response);
            }

            return new ApiResponse("Main content not found",false);
        }
        return new ApiResponse("Attachment not found",false);
    }
}
