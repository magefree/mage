package mage.abilities.text;

import java.util.UUID;

/**
 *
 * @author LevelX2
 * @param <E>
 */
public abstract class TextPartImpl<E> implements TextPart<E> {

    private final UUID id;

    public TextPartImpl() {
        this.id = UUID.randomUUID();
    }

    public TextPartImpl(final TextPartImpl textPartimpl) {
        this.id = textPartimpl.id;
    }

    @Override
    public UUID getId() {
        return id;
    }

}
