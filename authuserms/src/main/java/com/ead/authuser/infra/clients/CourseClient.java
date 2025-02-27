package com.ead.authuser.infra.clients;

import com.ead.authuser.api.model.dto.CourseDTO;
import com.ead.authuser.api.model.dto.ResponsePageDTO;
import com.ead.authuser.domain.service.interfaces.UtilsService;
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
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private UtilsService utilsService;

    @Value("${ead.api.url.course}")
    private String REQUEST_URL_COURSE;


    public Page<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable) {
        List<CourseDTO> searchResult = null;
        String url = REQUEST_URL_COURSE + utilsService.createUrlGetAllCoursesByUser(userId, pageable);
        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        try {
            var responseType = new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>(){};
            ResponseEntity<ResponsePageDTO<CourseDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {}", searchResult.size());

        }catch (HttpStatusCodeException e){
            log.error("Error request /courses {}", e);
        }
        log.info("Ending request /courses userId {}", userId);
        return new PageImpl<>(searchResult, pageable, searchResult.size());
    }

    public void deleteUserInCourse(UUID userId) {
        String url = REQUEST_URL_COURSE + "/courses/users/" + userId;
        restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
