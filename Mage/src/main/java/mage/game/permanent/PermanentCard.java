package mage.game.permanent;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.*;
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.util.UUID;

/**
 * Static permanent on the battlefield. There are possible multiple permanents per one card,
 * so be carefull for targets (ids are different) and ZCC (zcc is static for permanent).
 *
 * @author BetaSteward_at_googlemail.com
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PermanentCard extends PermanentImpl {

    protected int maxLevelCounters;
    // A copy of the origin card that was cast (this is not the original card, so it's possible to change some attribute to this blueprint to change attributes to the permanent if it enters the battlefield with e.g. a subtype)
    protected final Card card;
    // the number this permanent instance had
    protected int zoneChangeCounter;

    public PermanentCard(Card card, UUID controllerId, Game game) {
        super(card.getId(), card.getOwnerId(), controllerId, card.getName());

        // usage check: you must put to play only real card's part
        // if you use it in test code then call CardUtil.getDefaultCardSideForBattlefield for default side
        // it's a basic check and still allows to create permanent from instant or sorcery
        boolean goodForBattlefield;
        if (card instanceof DoubleFacedCard) {
            goodForBattlefield = false;
        } else if (card instanceof SplitCard) {
            // fused spells allowed (it uses main card)
            if (card.getSpellAbility() != null && !card.getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
                goodForBattlefield = false;
            } else {
                goodForBattlefield = true;
            }
        } else {
            goodForBattlefield = true;
        }
        if (!goodForBattlefield) {
            throw new IllegalArgumentException("ERROR, can't create permanent card from split or mdf: " + card.getName());
        }

        this.card = card;
        this.zoneChangeCounter = card.getZoneChangeCounter(game); // local value already set to the raised number
        init(card, game);
    }

    private void init(Card card, Game game) {
        if (card.isTransformable()
                && (game.getState().getValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + card.getMainCard().getId()) != null
                || NightboundAbility.checkCard(this, game))) {
            this.transformed = true;
        }
        copyFromCard(game);
        // if temporary added abilities to the spell/card exist, you need to add it to the permanent derived from that card
        Abilities<Ability> otherAbilities = game.getState().getAllOtherAbilities(card.getId());
        if (otherAbilities != null) {
            abilities.addAll(otherAbilities);
        }
        if (card instanceof LevelerCard) {
            maxLevelCounters = ((LevelerCard) card).getMaxLevelCounters();
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
        copyFromCard(game);
        super.reset(game);
    }

    protected void copyFromCard(final Game game) {
        Card cardToCopy = this.transformed ? this.card.getMainCard().getSecondCardFace() : this.card;
        this.name = cardToCopy.getName();
        this.abilities.clear();
        if (this.faceDown) {
            for (Ability ability : cardToCopy.getAbilities()) {
                if (ability.getWorksFaceDown()) {
                    this.abilities.add(ability.copy());
                }
            }
        } else {
            // copy only own abilities; all dynamic added abilities must be added in the parent call
            this.abilities = cardToCopy.getAbilities().copy();
            this.spellAbility = null; // will be set on first getSpellAbility call if card has one.
        }
        this.abilities.setControllerId(this.controllerId);
        this.abilities.setSourceId(objectId);
        this.cardType.clear();
        this.cardType.addAll(cardToCopy.getCardType());
        this.color = cardToCopy.getColor(null).copy();
        this.power = cardToCopy.getPower().copy();
        this.toughness = cardToCopy.getToughness().copy();
        this.startingLoyalty = cardToCopy.getStartingLoyalty();
        this.startingDefense = cardToCopy.getStartingDefense();
        this.frameColor = cardToCopy.getFrameColor(game).copy();
        this.frameStyle = cardToCopy.getFrameStyle();
        this.manaCost = cardToCopy.getManaCost().copy();
        if (cardToCopy instanceof PermanentCard) {
            this.maxLevelCounters = ((PermanentCard) cardToCopy).maxLevelCounters;
        }
        this.subtype.copyFrom(cardToCopy.getSubtype());
        this.supertype.clear();
        this.supertype.addAll(cardToCopy.getSuperType());

        this.setExpansionSetCode(cardToCopy.getExpansionSetCode());
        this.setCardNumber(cardToCopy.getCardNumber());
        this.rarity = cardToCopy.getRarity();
        this.usesVariousArt = cardToCopy.getUsesVariousArt();

        if (cardToCopy.getMeldsToCard() != null) {
            this.meldsToClazz = cardToCopy.getMeldsToCard().getClass();
        }
        this.nightCard = cardToCopy.isNightCard();
        this.flipCard = cardToCopy.isFlipCard();
        this.flipCardName = cardToCopy.getFlipCardName();
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
    public boolean turnFaceUp(Ability source, Game game, UUID playerId) {
        if (super.turnFaceUp(source, game, playerId)) {
            power.setModifiedBaseValue(power.getBaseValue());
            toughness.setModifiedBaseValue(toughness.getBaseValue());
            setManifested(false);
            setMorphed(false);
            return true;
        }
        return false;
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
    public int getManaValue() {
        if (isTransformed()) {
            // 711.4b While a double-faced permanent's back face is up, it has only the characteristics of its back face.
            // However, its converted mana cost is calculated using the mana cost of its front face. This is a change from previous rules.
            // If a permanent is copying the back face of a double-faced card (even if the card representing that copy
            // is itself a double-faced card), the converted mana cost of that permanent is 0.
            return getCard().getManaValue();
        }
        if (faceDown) { // game not neccessary
            return getManaCost().manaValue();
        }
        return super.getManaValue();

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

    @Override
    public Card getMainCard() {
        return card.getMainCard();
    }

    @Override
    public String toString() {
        return card.toString();
    }
}
