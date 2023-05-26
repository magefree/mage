package mage.cards;

import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class SplitCardHalfImpl extends CardImpl implements SplitCardHalf {

    SplitCard splitCardParent;

    public SplitCardHalfImpl(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs, SplitCard splitCardParent, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, cardTypes, costs, spellAbilityType);
        this.splitCardParent = splitCardParent;
    }

    public SplitCardHalfImpl(final SplitCardHalfImpl card) {
        super(card);
        this.splitCardParent = card.splitCardParent;
    }

    @Override
    public UUID getOwnerId() {
        return splitCardParent.getOwnerId();
    }

    @Override
    public String getExpansionSetCode() {
        return splitCardParent.getExpansionSetCode();
    }

    @Override
    public String getCardNumber() {
        return splitCardParent.getCardNumber();
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        return splitCardParent.moveToZone(toZone, source, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        return splitCardParent.moveToExile(exileId, name, source, game, appliedEffects);
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, Ability source) {
        return splitCardParent.removeFromZone(game, fromZone, source);
    }

    @Override
    public SplitCard getMainCard() {
        return splitCardParent;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        game.setZone(splitCardParent.getId(), zone);
        game.setZone(splitCardParent.getLeftHalfCard().getId(), zone);
        game.setZone(splitCardParent.getRightHalfCard().getId(), zone);
    }

    @Override
    public SplitCardHalf copy() {
        return new SplitCardHalfImpl(this);
    }

    @Override
    public void setParentCard(SplitCard card) {
        this.splitCardParent = card;
    }

    @Override
    public SplitCard getParentCard() {
        return this.splitCardParent;
    }
}
