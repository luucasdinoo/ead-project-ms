package com.ead.course.infra.clients;

import com.ead.course.api.model.CourseUserDTO;
import com.ead.course.api.model.ResponsePageDTO;
import com.ead.course.api.model.UserDTO;
import com.ead.course.domain.service.interfaces.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class AuthUserClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilsService utilsService;

    @Value("${ead.api.url.auth-user}")
    String REQUEST_URL_AUTH_USER;

    public Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        List<UserDTO> searchResult = null;
        String url = REQUEST_URL_AUTH_USER + utilsService.createUrlGetAllUsersByCourse(courseId, pageable);
        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        try {
            var responseType = new ParameterizedTypeReference<ResponsePageDTO<UserDTO>>(){};
            ResponseEntity<ResponsePageDTO<UserDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {}", searchResult.size());

        }catch (HttpStatusCodeException e){
            log.error("Error request /users {}", e);
        }
        log.info("Ending request /users userId {}", courseId);
        return new PageImpl<>(searchResult);
    }

    public ResponseEntity<UserDTO> getOneUserById(UUID userId){
        String url = REQUEST_URL_AUTH_USER + "/users/" + userId;
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
    }

    public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {
        String url = REQUEST_URL_AUTH_USER + "/users/" + userId + "/courses/subscription";
        var courseUserDto = new CourseUserDTO(courseId, userId);
        restTemplate.postForObject(url, courseUserDto, String.class);
    }
}
