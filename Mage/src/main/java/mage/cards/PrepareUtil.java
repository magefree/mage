package mage.cards;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

public final class PrepareUtil {

    private static final String PREPARE_COPY_KEY_PREFIX = "prepare-copy-";
    private static final UUID PREPARED_SPELLS_EXILE_ZONE_ID = UUID.nameUUIDFromBytes("prepared-spells-exile".getBytes());
    private static final String PREPARED_SPELLS_EXILE_ZONE_NAME = "Prepared Spells";

    private PrepareUtil() {
    }

    private static String getPrepareCopyKey(UUID permanentId) {
        return PREPARE_COPY_KEY_PREFIX + permanentId;
    }

    public static UUID getPrepareSpellCopyId(UUID permanentId, Game game) {
        Object value = game.getState().getValue(getPrepareCopyKey(permanentId));
        return value instanceof UUID ? (UUID) value : null;
    }

    private static void setPrepareSpellCopyId(UUID permanentId, UUID spellCopyId, Game game) {
        game.getState().setValue(getPrepareCopyKey(permanentId), spellCopyId);
    }

    public static PrepareCard getPrepareCard(Permanent permanent, Game game) {
        if (permanent == null) {
            return null;
        }
        MageObject basicObject = permanent.getBasicMageObject();
        if (basicObject instanceof PrepareCard) {
            return (PrepareCard) basicObject;
        }
        Card mainCard = permanent.getMainCard();
        if (mainCard instanceof PrepareCard) {
            return (PrepareCard) mainCard;
        }
        Card card = game.getCard(permanent.getId());
        return card instanceof PrepareCard ? (PrepareCard) card : null;
    }

    public static boolean isPrepared(Permanent permanent, Game game) {
        return permanent != null
                && permanent.isPrepared()
                && getPrepareSpellCopyId(permanent.getId(), game) != null;
    }

    public static boolean prepare(Permanent permanent, Game game, Ability source) {
        if (permanent == null || permanent.isPrepared()) {
            return false;
        }

        PrepareCard prepareCard = getPrepareCard(permanent, game);
        if (prepareCard == null) {
            return false;
        }

        if (!(prepareCard.getSpellCard() instanceof PrepareSpellCard)) {
            return false;
        }

        PrepareSpellCard spellCopy = ((PrepareSpellCard) prepareCard.getSpellCard()).copy();
        spellCopy.assignNewId();
        spellCopy.setParentCard(prepareCard);

        game.getState().addCopiedCard(spellCopy, Zone.EXILED);
        game.getExile().moveToAnotherZone(
                spellCopy,
                game,
                game.getExile().createZone(PREPARED_SPELLS_EXILE_ZONE_ID, PREPARED_SPELLS_EXILE_ZONE_NAME)
        );

        setPrepareSpellCopyId(permanent.getId(), spellCopy.getId(), game);
        permanent.setPrepared(true, game);
        return true;
    }

    public static boolean unprepare(Permanent permanent, Game game) {
        if (permanent == null || !permanent.isPrepared()) {
            return false;
        }
        cleanupPrepareSpellCopy(permanent, game);
        permanent.setPrepared(false, game);
        return true;
    }

    public static boolean cleanupPrepareSpellCopy(Permanent permanent, Game game) {
        if (permanent == null) {
            return false;
        }

        UUID spellCopyId = getPrepareSpellCopyId(permanent.getId(), game);
        if (spellCopyId != null) {
            Card spellCopy = game.getCard(spellCopyId);
            if (spellCopy != null) {
                game.getExile().removeCard(spellCopy);
            }
            game.setZone(spellCopyId, Zone.OUTSIDE);
            setPrepareSpellCopyId(permanent.getId(), null, game);
        }
        return true;
    }

    public static boolean canCastPreparedSpell(UUID playerId, PrepareSpellCard spellCard, Game game) {
        if (playerId == null || spellCard == null || spellCard.getParentCard() == null) {
            return false;
        }

        Permanent parentPermanent = game.getPermanent(spellCard.getParentCard().getId());
        if (parentPermanent == null
                || !parentPermanent.isPrepared()
                || !parentPermanent.isControlledBy(playerId)) {
            return false;
        }

        UUID linkedSpellCopyId = getPrepareSpellCopyId(parentPermanent.getId(), game);
        return linkedSpellCopyId != null
                && linkedSpellCopyId.equals(spellCard.getId())
                && game.getState().getZone(spellCard.getId()) == Zone.EXILED;
    }

    public static void onPrepareSpellCast(PrepareSpellCard spellCard, UUID controllerId, Game game) {
        if (spellCard == null || spellCard.getParentCard() == null) {
            return;
        }

        Permanent parentPermanent = game.getPermanent(spellCard.getParentCard().getId());
        if (parentPermanent == null
                || !parentPermanent.isPrepared()
                || !parentPermanent.isControlledBy(controllerId)) {
            return;
        }

        UUID linkedSpellCopyId = getPrepareSpellCopyId(parentPermanent.getId(), game);
        if (linkedSpellCopyId != null && linkedSpellCopyId.equals(spellCard.getId())) {
            game.getExile().removeCard(spellCard);
            setPrepareSpellCopyId(parentPermanent.getId(), null, game);
        }
        parentPermanent.setPrepared(false, game);
    }

    public static void onPrepareSpellRemovedFromExile(PrepareSpellCard spellCard, Game game) {
        if (spellCard == null || spellCard.getParentCard() == null) {
            return;
        }

        Permanent parentPermanent = game.getPermanent(spellCard.getParentCard().getId());
        if (parentPermanent == null || !parentPermanent.isPrepared()) {
            return;
        }

        UUID linkedSpellCopyId = getPrepareSpellCopyId(parentPermanent.getId(), game);
        if (linkedSpellCopyId != null && linkedSpellCopyId.equals(spellCard.getId())) {
            setPrepareSpellCopyId(parentPermanent.getId(), null, game);
            parentPermanent.setPrepared(false, game);
        }
    }
}
