package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SourceTargetPointer extends TargetPointerImpl {
    private final boolean allowCard;

    /**
     * Target pointer that always "targets" whatever the source of the ability is.
     * <p>
     * There is no locked-in variant: getSourcePermanentIfItStillExists already pins the source to
     * the zone change counter the ability was created with, so following it is the same thing as
     * snapshotting it.
     */
    public SourceTargetPointer() {
        this(false);
    }

    /**
     * @param allowCard also point at the source while it is not a permanent, for effects that grant
     *                  an ability to the source card itself (a spell on the stack, a card in hand)
     */
    public SourceTargetPointer(boolean allowCard) {
        super();
        this.allowCard = allowCard;
        this.targetDescription = "{this}";
    }

    public SourceTargetPointer(final SourceTargetPointer other) {
        super(other);
        this.allowCard = other.allowCard;
    }


    @Override
    public void init(Game game, Ability source) {
        if (isInitialized()) {
            return;
        }
        setInitialized();
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            // the source is not a permanent: only an ability granted to the card itself still applies
            if (allowCard && source.getSourceId() != null) {
                List<UUID> cardList = new ArrayList<>();
                cardList.add(source.getSourceId());
                return cardList;
            }
            return Collections.emptyList();
        }
        List<UUID> list = new ArrayList<>();
        list.add(permanent.getId());
        return list;
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        throw new IllegalStateException("Attempted to get first target on SourceTargetPointer (bad Effect usage)");
    }

    @Override
    public SourceTargetPointer copy() {
        return new SourceTargetPointer(this);
    }

    @Override
    public Permanent getFirstTargetPermanentOrLKI(Game game, Ability source) {
        throw new IllegalStateException("Attempted to get first target on SourceTargetPointer (bad Effect usage)");
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
