package com.ead.course.domain.repository.specs;

import com.ead.course.domain.model.entity.CourseModel;
import com.ead.course.domain.model.entity.CourseUserModel;
import com.ead.course.domain.model.entity.LessonModel;
import com.ead.course.domain.model.entity.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class),
    })
    public interface CourseSpec extends Specification<CourseModel>{}

    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpec extends Specification<ModuleModel>{}

    @Spec(path = "title", spec = Like.class)
    public interface LessonSpec extends Specification<LessonModel>{}

    public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {
        return (root, query, builder) -> {
            query.distinct(true);
            Root<CourseModel> course = query.from(CourseModel.class);
            Expression<Collection<ModuleModel>> coursesModules = course.get("modules");
            return builder.and(builder.equal(course.get("courseId"), courseId), builder.isMember(root, coursesModules));
        };
    }

    public static Specification<LessonModel> lessonModuleId(final UUID moduleId) {
        return (root, query, builder) -> {
            query.distinct(true);
            Root<ModuleModel> module = query.from(ModuleModel.class);
            Expression<Collection<LessonModel>> moduleLessons = module.get("lessons");
            return builder.and(builder.equal(root.get("module").get("moduleId"), moduleId), builder.isMember(root, moduleLessons));
        };
    }

    public static Specification<CourseModel> courseUserId(final UUID userId) {
        return (root, query, builder) ->{
          query.distinct(true);
            Join<CourseModel, CourseUserModel> courseProd = root.join("coursesUsers");
            return builder.equal(courseProd.get("userId"), userId);
        };
    }
}
