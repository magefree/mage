package mage.game.permanent;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.LevelerCard;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.SplitCard;
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
    protected Card card;
    // the number this permanent instance had
    protected int zoneChangeCounter;

    public PermanentCard(Card card, UUID controllerId, Game game) {
        super(card.getId(), card.getOwnerId(), controllerId, card.getName());

        // usage check: you must put to play only real card's part
        // if you use it in test code then call CardUtil.getDefaultCardSideForBattlefield for default side
        // it's a basic check and still allows to create permanent from instant or sorcery
        boolean goodForBattlefield = true;
        if (card instanceof ModalDoubleFacesCard) {
            goodForBattlefield = false;
        } else if (card instanceof SplitCard) {
            // fused spells allowed (it uses main card)
            if (card.getSpellAbility() != null && !card.getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
                goodForBattlefield = false;
            }
        }
        if (!goodForBattlefield) {
            throw new IllegalArgumentException("ERROR, can't create permanent card from split or mdf: " + card.getName());
        }

        this.card = card;
        this.zoneChangeCounter = card.getZoneChangeCounter(game); // local value already set to the raised number
        init(card, game);
    }

    private void init(Card card, Game game) {
        power = card.getPower().copy();
        toughness = card.getToughness().copy();
        startingLoyalty = card.getStartingLoyalty();
        copyFromCard(card, game);
        // if temporary added abilities to the spell/card exist, you need to add it to the permanent derived from that card
        Abilities<Ability> otherAbilities = game.getState().getAllOtherAbilities(card.getId());
        if (otherAbilities != null) {
            abilities.addAll(otherAbilities);
        }
        if (card instanceof LevelerCard) {
            maxLevelCounters = ((LevelerCard) card).getMaxLevelCounters();
        }
        if (card.isTransformable()) {
            if (game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + getId()) != null
                    || NightboundAbility.checkCard(this, game)) {
                game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + getId(), null);
                TransformAbility.transformPermanent(this, getSecondCardFace(), game, null);
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
            // copy only own abilities; all dynamic added abilities must be added in the parent call
            this.abilities = card.getAbilities().copy();
            this.spellAbility = null; // will be set on first getSpellAbility call if card has one.
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
        this.subtype.copyFrom(card.getSubtype());
        this.supertype.clear();
        this.supertype.addAll(card.getSuperType());
        this.expansionSetCode = card.getExpansionSetCode();
        this.rarity = card.getRarity();
        this.cardNumber = card.getCardNumber();
        this.usesVariousArt = card.getUsesVariousArt();

        if (card.getSecondCardFace() != null) {
            this.secondSideCardClazz = card.getSecondCardFace().getClass();
        }
        if (card.getMeldsToCard() != null) {
            this.meldsToClazz = card.getMeldsToCard().getClass();
        }
        this.nightCard = card.isNightCard();
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
