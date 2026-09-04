package com.constructWeek3.assure.service;

import com.constructWeek3.assure.dto.MembersDTO;
import com.constructWeek3.assure.entity.Members;
import com.constructWeek3.assure.exception.UserExists;
import com.constructWeek3.assure.repository.MembersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembersServiceTest {

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private ModelMapper modelmapper;

    @InjectMocks
    private MembersService membersService;

    private Members member;
    private MembersDTO membersDTO;

    @BeforeEach
    void setUp() {
        member = new Members();
        member.setMember_id(1L);
        member.setName("Alice");
        member.setEmail("alice@example.com");
        member.setMobile("9876543210");

        membersDTO = new MembersDTO();
        membersDTO.setName("Alice");
        membersDTO.setEmail("alice@example.com");
        membersDTO.setMobile("9876543210");
    }

    // ---- getallmembers tests ----

    @Test
    void getallmembers_returnsListOfMembersDTO() {
        List<Members> membersList = List.of(member);
        List<MembersDTO> membersDTOList = List.of(membersDTO);

        when(membersRepository.findAll()).thenReturn(membersList);
        when(modelmapper.map(any(), any(java.lang.reflect.Type.class))).thenReturn(membersDTOList);

        List<MembersDTO> result = membersService.getallmembers();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getallmembers_emptyList_returnsEmptyList() {
        when(membersRepository.findAll()).thenReturn(new ArrayList<>());
        when(modelmapper.map(any(), any(java.lang.reflect.Type.class))).thenReturn(new ArrayList<>());

        List<MembersDTO> result = membersService.getallmembers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ---- memberbyid tests ----

    @Test
    void memberbyid_validId_returnsMembersDTO() {
        when(membersRepository.findById(1L)).thenReturn(Optional.of(member));
        doAnswer(invocation -> {
            MembersDTO dto = invocation.getArgument(1);
            dto.setName("Alice");
            return null;
        }).when(modelmapper).map(any(Members.class), any(MembersDTO.class));

        MembersDTO result = membersService.memberbyid(1L);

        assertNotNull(result);
    }

    // ---- postmember tests ----

    @Test
    void postmember_newMember_savesMemberAndReturnsSuccess() {
        when(membersRepository.findAll()).thenReturn(new ArrayList<>());
        when(membersRepository.save(any(Members.class))).thenReturn(member);

        String result = membersService.postmember(membersDTO);

        assertEquals("saved", result);
        verify(membersRepository, times(1)).save(any(Members.class));
    }

    @Test
    void postmember_duplicateEmail_throwsUserExists() {
        Members existingMember = new Members();
        existingMember.setEmail("alice@example.com");
        existingMember.setMobile("1111111111");

        when(membersRepository.findAll()).thenReturn(List.of(existingMember));

        assertThrows(UserExists.class, () -> membersService.postmember(membersDTO));
    }

    @Test
    void postmember_duplicateMobile_throwsUserExists() {
        Members existingMember = new Members();
        existingMember.setEmail("other@example.com");
        existingMember.setMobile("9876543210");

        when(membersRepository.findAll()).thenReturn(List.of(existingMember));

        assertThrows(UserExists.class, () -> membersService.postmember(membersDTO));
    }

    // ---- updatemember tests ----

    @Test
    void updatemember_existingMember_updatesAndReturnsMembersDTO() {
        Members existingMember = new Members();
        existingMember.setEmail("alice@example.com");
        existingMember.setMobile("9876543210");

        when(membersRepository.findAll()).thenReturn(List.of(existingMember));
        when(membersRepository.save(any(Members.class))).thenReturn(existingMember);

        MembersDTO result = membersService.updatemember(membersDTO);

        assertNotNull(result);
        assertEquals("alice@example.com", result.getEmail());
    }

    @Test
    void updatemember_memberNotFound_throwsUserExists() {
        Members existingMember = new Members();
        existingMember.setEmail("other@example.com");

        when(membersRepository.findAll()).thenReturn(List.of(existingMember));

        assertThrows(UserExists.class, () -> membersService.updatemember(membersDTO));
    }

    @Test
    void updatemember_saveThrowsException_returnsNull() {
        Members existingMember = new Members();
        existingMember.setEmail("alice@example.com");

        when(membersRepository.findAll()).thenReturn(List.of(existingMember));
        when(membersRepository.save(any(Members.class))).thenThrow(new RuntimeException("DB error"));

        MembersDTO result = membersService.updatemember(membersDTO);

        assertNull(result);
    }

    // ---- deletemember tests ----

    @Test
    void deletemember_validId_deletesAndReturnsDeleted() {
        when(membersRepository.findById(1L)).thenReturn(Optional.of(member));

        String result = membersService.deletemember(1L);

        assertEquals("Deleted", result);
        verify(membersRepository, times(1)).delete(member);
    }

    @Test
    void deletemember_deleteThrowsException_returnsError() {
        when(membersRepository.findById(1L)).thenReturn(Optional.of(member));
        doThrow(new RuntimeException("DB error")).when(membersRepository).delete(any(Members.class));

        String result = membersService.deletemember(1L);

        assertEquals("Error", result);
    }
}
