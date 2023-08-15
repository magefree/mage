package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * Warning: please test with a lot of care when using this class for new things.
 * <p>
 * A static Ability linked to an Effect.
 * The parent Ability does take responsability of setting the id for the child.
 *
 * @author Susucr
 */
public class LinkedEffectIdStaticAbility extends SimpleStaticAbility {

    public interface ChildEffect extends Effect {
        /**
         * Set the link for the child.
         */
        void setParentLinkHandshake(UUID parentLinkHandshake);

        /**
         * The child Id should only change on copy when the parent wants it to.
         */
        void manualNewId();
    }


    /**
     * The handshake UUID between this parent ability and its child.
     */
    private UUID linkedHandshake;

    public LinkedEffectIdStaticAbility(ChildEffect effect) {
        this(Zone.BATTLEFIELD, effect);
    }

    public LinkedEffectIdStaticAbility(Zone zone, ChildEffect effect) {
        super(Zone.BATTLEFIELD, effect);
        this.linkedHandshake = UUID.randomUUID();
        initHandshake();
        setEffectIdManually();
    }

    private LinkedEffectIdStaticAbility(final LinkedEffectIdStaticAbility effect) {
        super(effect);
        this.linkedHandshake = UUID.randomUUID();
        initHandshake();
    }

    @Override
    public LinkedEffectIdStaticAbility copy() {
        return new LinkedEffectIdStaticAbility(this);
    }

    private void initHandshake() {
        this.linkedHandshake = UUID.randomUUID();
        CardUtil.castStream(this.getEffects().stream(), ChildEffect.class)
                .filter(Objects::nonNull)
                .forEach(e -> e.setParentLinkHandshake(linkedHandshake));
    }

    public void setEffectIdManually() {
        CardUtil.castStream(this.getEffects().stream(), ChildEffect.class)
                .filter(Objects::nonNull)
                .forEach(e -> e.manualNewId());
    }

    public boolean checkLinked(UUID handshake) {
        return linkedHandshake.equals(handshake);
    }

    @Override
    public void newId() {
        super.newId();
    }
}

