package uz.bob.app_phone_purchases.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.bob.app_phone_purchases.entity.Role;
import uz.bob.app_phone_purchases.payload.ApiResponse;
import uz.bob.app_phone_purchases.payload.RoleDto;
import uz.bob.app_phone_purchases.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    public ApiResponse addRole(RoleDto roleDto) {

        if (roleRepository.existsByName(roleDto.getName()))
            return new ApiResponse("Role with the name \""+roleDto.getName()+"\" already exist",false);
        Role role=new Role(
                roleDto.getName(),
                roleDto.getPermissionList(),
                roleDto.getDescription()
        );

        roleRepository.save(role);
        return new ApiResponse("Role has been created",true);
    }

    public ApiResponse editRole(Long id, RoleDto roleDTO) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found",false);
        }
        if (roleRepository.existsByNameAndIdNot(roleDTO.getName(), id)) {
            return new ApiResponse("Role with name and with Id already exist",true);
        }
        Role role = optionalRole.get();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        role.setPermissionList(roleDTO.getPermissionList());

        roleRepository.save(role);
        return new ApiResponse("Role successfully edited",true);
    }


    public List<Role> view(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Role> rolePage = roleRepository.findAll(pageable);
        return rolePage.getContent();
    }

    public Role getRole(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }

    public boolean delete(Long id) {
        try {
            roleRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
