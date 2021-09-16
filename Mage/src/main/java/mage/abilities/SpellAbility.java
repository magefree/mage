package mage.abilities;

import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.keyword.FlashAbility;
import mage.cards.AdventureCardSpell;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SpellAbility extends ActivatedAbilityImpl {

    protected SpellAbilityType spellAbilityType;
    protected SpellAbilityCastMode spellAbilityCastMode;
    protected String cardName;

    public SpellAbility(ManaCost cost, String cardName) {
        this(cost, cardName, Zone.HAND);
    }

    public SpellAbility(ManaCost cost, String cardName, Zone zone) {
        this(cost, cardName, zone, SpellAbilityType.BASE);
    }

    public SpellAbility(ManaCost cost, String cardName, Zone zone, SpellAbilityType spellAbilityType) {
        this(cost, cardName, zone, spellAbilityType, SpellAbilityCastMode.NORMAL);
    }

    public SpellAbility(ManaCost cost, String cardName, Zone zone, SpellAbilityType spellAbilityType, SpellAbilityCastMode spellAbilityCastMode) {
        super(AbilityType.SPELL, zone);
        this.cardName = cardName;
        this.spellAbilityType = spellAbilityType;
        this.spellAbilityCastMode = spellAbilityCastMode;
        this.addManaCost(cost);
        setSpellName();
    }

    public SpellAbility(final SpellAbility ability) {
        super(ability);
        this.spellAbilityType = ability.spellAbilityType;
        this.spellAbilityCastMode = ability.spellAbilityCastMode;
        this.cardName = ability.cardName;
    }

    /*
     * 7/5/19 - jgray1206 - Moved null != game.getContinuesEffects()... into this method instead of having it in
     *                      canActivate. There are abilities that directly use this method that should know when spells
     *                      can be casted that are affected by the CastAsInstant effect.
     *                      (i.e. Vizier of the Menagerie and issue #5816)
     */
    public boolean spellCanBeActivatedRegularlyNow(UUID playerId, Game game) {
        MageObject object = game.getObject(sourceId);
        if (object == null) {
            return false;
        }

        // forced to cast (can be part id or main id)
        Set<UUID> idsToCheck = new HashSet<>();
        idsToCheck.add(object.getId());
        if (object instanceof Card && !(object instanceof AdventureCardSpell)) {
            idsToCheck.add(((Card) object).getMainCard().getId());
        }
        for (UUID idToCheck : idsToCheck) {
            if (game.getState().getValue("PlayFromNotOwnHandZone" + idToCheck) != null) {
                return (Boolean) game.getState().getValue("PlayFromNotOwnHandZone" + idToCheck);  // card like Chandra, Torch of Defiance +1 loyal ability)
            }
        }

        return null != game.getContinuousEffects().asThough(sourceId, AsThoughEffectType.CAST_AS_INSTANT, this, playerId, game) // check this first to allow Offering in main phase
                || timing == TimingRule.INSTANT
                || object.isInstant(game)
                || object.hasAbility(FlashAbility.getInstance(), game)
                || game.canPlaySorcery(playerId);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // spells can be cast from non hand zones, so must use custom check
        // no super.canActivate() call

        if (this.spellCanBeActivatedRegularlyNow(playerId, game)) {
            if (spellAbilityType == SpellAbilityType.SPLIT
                    || spellAbilityType == SpellAbilityType.SPLIT_AFTERMATH) {
                return ActivationStatus.getFalse();
            }

            // play from not own hand
            ApprovingObject approvingObject = game.getContinuousEffects().asThough(getSourceId(), AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, this, playerId, game);
            if (approvingObject == null) {
                Card card = game.getCard(sourceId);
                if (!(card != null && card.isOwnedBy(playerId))) {
                    return ActivationStatus.getFalse();
                }
            }

            // play restrict
            // Check if rule modifying events prevent to cast the spell in check playable mode
            if (game.inCheckPlayableState()) {
                Card card = game.getCard(sourceId);
                GameEvent castEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL, this.getId(), this, playerId);
                castEvent.setZone(card == null ? null : game.getState().getZone(card.getMainCard().getId()));
                if (game.getContinuousEffects().preventedByRuleModification(
                        castEvent, this, game, true)) {
                    return ActivationStatus.getFalse();
                }
            }

            // no mana restrict
            // Alternate spell abilities (Flashback, Overload) can't be cast with no mana to pay option
            if (getSpellAbilityType() == SpellAbilityType.BASE_ALTERNATE) {
                Player player = game.getPlayer(playerId);
                if (player != null
                        && player.getCastSourceIdWithAlternateMana().contains(getSourceId())) {
                    return ActivationStatus.getFalse();
                }
            }

            // can pay all costs and choose targets
            if (costs.canPay(this, this, playerId, game)) {
                if (getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
                    SplitCard splitCard = (SplitCard) game.getCard(getSourceId());
                    if (splitCard != null) {
                        // fused can be called from hand only, so not permitting object allows or other zones checks
                        // see https://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/251926-snapcaster-mage-and-fuse
                        if (game.getState().getZone(splitCard.getId()) == Zone.HAND) {
                            return new ActivationStatus(splitCard.getLeftHalfCard().getSpellAbility().canChooseTarget(game, playerId)
                                    && splitCard.getRightHalfCard().getSpellAbility().canChooseTarget(game, playerId), null);
                        }
                    }
                    return ActivationStatus.getFalse();
                } else {
                    return new ActivationStatus(canChooseTarget(game, playerId), approvingObject);
                }
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public String getGameLogMessage(Game game) {
        return getMessageText(game);
    }

    @Override
    public String getRule(boolean all) {
        if (all) {
            return new StringBuilder(super.getRule(all)).append(name).toString();
        }
        return super.getRule(false);
    }

    public void clear() {
        getTargets().clearChosen();
        this.manaCosts.clearPaid();
        this.costs.clearPaid();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public SpellAbility copy() {
        return new SpellAbility(this);
    }

    public SpellAbility copySpell(Card originalCard, Card copiedCard) {
        // all copied spells must have own copied card
        Map<UUID, MageObject> mapOldToNew = CardUtil.getOriginalToCopiedPartsMap(originalCard, copiedCard);
        if (!mapOldToNew.containsKey(this.getSourceId())) {
            throw new IllegalStateException("Can't find source id after copy: " + originalCard.getName() + " -> " + copiedCard.getName());
        }
        UUID copiedSourceId = mapOldToNew.getOrDefault(this.getSourceId(), copiedCard).getId();

        SpellAbility spell = new SpellAbility(this);
        spell.newId();
        spell.setSourceId(copiedSourceId);
        return spell;
    }

    public SpellAbilityType getSpellAbilityType() {
        return spellAbilityType;
    }

    public void setSpellAbilityType(SpellAbilityType spellAbilityType) {
        this.spellAbilityType = spellAbilityType;
    }

    public String getCardName() {
        return cardName;
    }

    public int getConvertedXManaCost(Card card) {
        int xMultiplier = 0;
        int amount = 0;
        if (card == null) {
            return 0;
        }

        // mana cost instances
        for (ManaCost manaCost : card.getManaCost()) {
            if (manaCost instanceof VariableManaCost) {
                xMultiplier = ((VariableManaCost) manaCost).getXInstancesCount();
                break;
            }
        }

        // mana cost final X value
        boolean hasNonManaXCost = false;
        for (Cost cost : getCosts()) {
            if (cost instanceof VariableCost) {
                hasNonManaXCost = true;
                amount = ((VariableCost) cost).getAmount();
                break;
            }
        }

        if (!hasNonManaXCost) {
            amount = getManaCostsToPay().getX();
        }
        return amount * xMultiplier;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
        setSpellName();
    }

    private void setSpellName() {
        switch (spellAbilityType) {
            case SPLIT_FUSED:
                this.name = "Cast fused " + cardName;
                break;
            default:
                this.name = "Cast " + cardName + (this.spellAbilityCastMode != SpellAbilityCastMode.NORMAL ? " using " + spellAbilityCastMode.toString() : "");
        }
    }

    public SpellAbilityCastMode getSpellAbilityCastMode() {
        return spellAbilityCastMode;
    }

    public void setSpellAbilityCastMode(SpellAbilityCastMode spellAbilityCastMode) {
        this.spellAbilityCastMode = spellAbilityCastMode;
        setSpellName();
    }

    public SpellAbility getSpellAbilityToResolve(Game game) {
        return this;
    }

    /**
     * Returns a card object with the spell characteristics like calor, types,
     * subtypes etc. E.g. if you cast a Bestow card as enchantment, the
     * characteristics don't include the creature type.
     *
     * @param game
     * @return card object with the spell characteristics
     */
    public Card getCharacteristics(Game game) {
        Card spellCharacteristics = game.getSpell(this.getId());
        if (spellCharacteristics == null) {
            spellCharacteristics = game.getCard(this.getSourceId());
        }
        if (spellCharacteristics != null) {
            if (getSpellAbilityCastMode() != SpellAbilityCastMode.NORMAL) {
                spellCharacteristics = getSpellAbilityCastMode().getTypeModifiedCardObjectCopy(spellCharacteristics, game);
            }
        }
        return spellCharacteristics;
    }

    public static SpellAbility getSpellAbilityFromEvent(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CAST_SPELL) {
            return null;
        }

        Card card = game.getCard(event.getSourceId());
        if (card != null) {
            Optional<Ability> ability = card.getAbilities(game).get(event.getTargetId());
            if (ability.isPresent() && ability.get() instanceof SpellAbility) {
                return (SpellAbility) ability.get();
            }
            return card.getSpellAbility();
        }
        return null;
    }

    public void setId(UUID idToUse) {
        this.id = idToUse;
    }
}
