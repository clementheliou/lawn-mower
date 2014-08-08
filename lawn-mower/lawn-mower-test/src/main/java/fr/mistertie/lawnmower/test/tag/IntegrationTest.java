package fr.mistertie.lawnmower.test.tag;

import org.scalatest.TagAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation used to tag integration tests written with ScalaTest.
 *
 * @see {@link org.scalatest.TagAnnotation}
 */
@Retention(RUNTIME)
@TagAnnotation
@Target({METHOD, TYPE})
public @interface IntegrationTest {
}
