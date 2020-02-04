
package mage.abilities.text;

import java.io.Serializable;
import java.util.UUID;
import mage.util.Copyable;

/**
 *
 * @author LevelX2
 * @param <E>
 */
public interface TextPart<E> extends Serializable, Copyable<TextPart> {

    UUID getId();

    String getText();

    E getBaseValue();

    E getCurrentValue();

    void replaceWith(E o);

    void reset();
}
