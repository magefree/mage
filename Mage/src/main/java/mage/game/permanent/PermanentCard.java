
package mage.game.permanent;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.LevelerCard;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentCard extends PermanentImpl {

    protected int maxLevelCounters;
    // A copy of the origin card that was cast (this is not the original card, so it's possible to chnage some attribute to this blueprint to change attributes to the permanent if it enters the battlefield with e.g. a subtype)
    protected Card card;
    // the number this permanent instance had
    protected int zoneChangeCounter;

    public PermanentCard(Card card, UUID controllerId, Game game) {
        super(card.getId(), card.getOwnerId(), controllerId, card.getName());

        this.card = card;
        this.zoneChangeCounter = card.getZoneChangeCounter(game); // local value already set to the raised number
        init(card, game);
    }

    private void init(Card card, Game game) {
        power = card.getPower().copy();
        toughness = card.getToughness().copy();
        copyFromCard(card, game);
        // if temporary added abilities to the spell/card exist, you need to add it to the permanent derived from that card
        Abilities<Ability> otherAbilities = game.getState().getAllOtherAbilities(card.getId());
        if (otherAbilities != null) {
            abilities.addAll(otherAbilities);
        }
        /*if (card.getCardType().contains(CardType.PLANESWALKER)) {
         this.loyalty = new MageInt(card.getLoyalty().getValue());
         }*/
        if (card instanceof LevelerCard) {
            maxLevelCounters = ((LevelerCard) card).getMaxLevelCounters();
        }
        if (isTransformable()) {
            if (game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + getId()) != null) {
                game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + getId(), null);
                setTransformed(true);
                TransformAbility.transform(this, getSecondCardFace(), game);
            }
        }
    }

    public PermanentCard(final PermanentCard permanent) {
        super(permanent);
        this.card = permanent.card.copy();
        this.maxLevelCounters = permanent.maxLevelCounters;
        this.zoneChangeCounter = permanent.zoneChangeCounter;
    }

    @Override
    public void reset(Game game) {
        // when the permanent is reset, copy all original values from the card
        // must copy card each reset so that the original values don't get modified
        copyFromCard(card, game);
        power.resetToBaseValue();
        toughness.resetToBaseValue();
        super.reset(game);
    }

    protected void copyFromCard(final Card card, final Game game) {
        this.name = card.getName();
        this.abilities.clear();
        if (this.faceDown) {
            for (Ability ability : card.getAbilities()) {
                if (ability.getWorksFaceDown()) {
                    this.abilities.add(ability.copy());
                }
            }
        } else {
            this.abilities = card.getAbilities().copy();
        }
        this.abilities.setControllerId(this.controllerId);
        this.abilities.setSourceId(objectId);
        this.cardType.clear();
        this.cardType.addAll(card.getCardType());
        this.color = card.getColor(null).copy();
        this.frameColor = card.getFrameColor(game).copy();
        this.frameStyle = card.getFrameStyle();
        this.manaCost = card.getManaCost().copy();
        if (card instanceof PermanentCard) {
            this.maxLevelCounters = ((PermanentCard) card).maxLevelCounters;
        }
        this.subtype.clear();
        this.subtype.addAll(card.getSubtype(game));
        this.isAllCreatureTypes = card.isAllCreatureTypes();
        this.supertype.clear();
        supertype.addAll(card.getSuperType());
        this.expansionSetCode = card.getExpansionSetCode();
        this.rarity = card.getRarity();
        this.cardNumber = card.getCardNumber();
        this.usesVariousArt = card.getUsesVariousArt();

        transformable = card.isTransformable();
        if (transformable) {
            this.nightCard = card.isNightCard();
            if (!this.nightCard) {
                this.secondSideCard = card.getSecondCardFace();
                this.secondSideCardClazz = this.secondSideCard.getClass();
            }
        }
        this.flipCard = card.isFlipCard();
        this.flipCardName = card.getFlipCardName();
    }

    @Override
    public MageObject getBasicMageObject(Game game) {
        return card;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public PermanentCard copy() {
        return new PermanentCard(this);
    }

    public int getMaxLevelCounters() {
        return this.maxLevelCounters;
    }

    @Override
    public boolean turnFaceUp(Game game, UUID playerId) {
        if (super.turnFaceUp(game, playerId)) {
            power.modifyBaseValue(power.getBaseValue());
            toughness.modifyBaseValue(toughness.getBaseValue());
            setManifested(false);
            setMorphed(false);
            return true;
        }
        return false;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (this.isTransformed() && card.getSecondCardFace() != null) {
            card.getSecondCardFace().adjustTargets(ability, game);
        } else {
            card.adjustTargets(ability, game);
        }
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (this.isTransformed() && card.getSecondCardFace() != null) {
            card.getSecondCardFace().adjustCosts(ability, game);
        } else {
            card.adjustCosts(ability, game);
        }
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        if (faceDown) { // face down permanent has always {0} mana costs
            manaCost.clear();
            return manaCost;
        }
        return super.getManaCost();
    }

    @Override
    public int getConvertedManaCost() {
        if (isTransformed()) {
            // 711.4b While a double-faced permanent's back face is up, it has only the characteristics of its back face.
            // However, its converted mana cost is calculated using the mana cost of its front face. This is a change from previous rules.
            // If a permanent is copying the back face of a double-faced card (even if the card representing that copy
            // is itself a double-faced card), the converted mana cost of that permanent is 0.
            return getCard().getConvertedManaCost();
        }
        if (faceDown) { // game not neccessary
            return getManaCost().convertedManaCost();
        }
        return super.getConvertedManaCost();

    }

    @Override
    public int getZoneChangeCounter(Game game) {
        // permanent value of zone change counter stays always the same without exception of update during the process of putting the permanent onto the battlefield
        return zoneChangeCounter;
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        card.updateZoneChangeCounter(game, event);
        zoneChangeCounter = card.getZoneChangeCounter(game);
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        card.setZoneChangeCounter(value, game);
    }

}
