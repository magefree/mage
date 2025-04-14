package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.ModalDoubleFacedCard;
import mage.cards.SplitCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * Base class for Flashback and Harmonize and any future ability which works similarly
 *
 * @author TheElk801
 */
public abstract class CastFromGraveyardAbility extends SpellAbility {

    protected String abilityName;
    private SpellAbility spellAbilityToResolve;

    protected CastFromGraveyardAbility(Card card, Cost cost, SpellAbilityCastMode spellAbilityCastMode) {
        super(null, "", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE, spellAbilityCastMode);
        this.setAdditionalCostsRuleVisible(false);
        this.name = spellAbilityCastMode + " " + cost.getText();
        this.addCost(cost);
        this.timing = card.isSorcery() ? TimingRule.SORCERY : TimingRule.INSTANT;
    }

    protected CastFromGraveyardAbility(final CastFromGraveyardAbility ability) {
        super(ability);
        this.abilityName = ability.abilityName;
        this.spellAbilityToResolve = ability.spellAbilityToResolve;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // flashback ability dynamicly added to all card's parts (split cards)
        if (!super.canActivate(playerId, game).canActivate()) {
            return ActivationStatus.getFalse();
        }
        Card card = game.getCard(getSourceId());
        if (card == null) {
            return ActivationStatus.getFalse();
        }
        // Card must be in the graveyard zone
        if (game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return ActivationStatus.getFalse();
        }
        // Cards with no Mana Costs cant't be flashbacked (e.g. Ancestral Vision)
        if (card.getManaCost().isEmpty()) {
            return ActivationStatus.getFalse();
        }
        // CastFromGraveyard can never cast a split card by Fuse, because Fuse only works from hand
        // https://tappedout.net/mtg-questions/snapcaster-mage-and-flashback-on-a-fuse-card-one-or-both-halves-legal-targets/
        if (card instanceof SplitCard) {
            if (((SplitCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                return ((SplitCard) card).getLeftHalfCard().getSpellAbility().canActivate(playerId, game);
            } else if (((SplitCard) card).getRightHalfCard().getName().equals(abilityName)) {
                return ((SplitCard) card).getRightHalfCard().getSpellAbility().canActivate(playerId, game);
            }
        } else if (card instanceof ModalDoubleFacedCard) {
            if (((ModalDoubleFacedCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                return ((ModalDoubleFacedCard) card).getLeftHalfCard().getSpellAbility().canActivate(playerId, game);
            } else if (((ModalDoubleFacedCard) card).getRightHalfCard().getName().equals(abilityName)) {
                return ((ModalDoubleFacedCard) card).getRightHalfCard().getSpellAbility().canActivate(playerId, game);
            }
        }
        return card.getSpellAbility().canActivate(playerId, game);
    }

    @Override
    public SpellAbility getSpellAbilityToResolve(Game game) {
        Card card = game.getCard(getSourceId());
        if (card == null || spellAbilityToResolve != null) {
            return spellAbilityToResolve;
        }
        SpellAbility spellAbilityCopy;
        if (card instanceof SplitCard) {
            if (((SplitCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                spellAbilityCopy = ((SplitCard) card).getLeftHalfCard().getSpellAbility().copy();
            } else if (((SplitCard) card).getRightHalfCard().getName().equals(abilityName)) {
                spellAbilityCopy = ((SplitCard) card).getRightHalfCard().getSpellAbility().copy();
            } else {
                spellAbilityCopy = null;
            }
        } else if (card instanceof ModalDoubleFacedCard) {
            if (((ModalDoubleFacedCard) card).getLeftHalfCard().getName().equals(abilityName)) {
                spellAbilityCopy = ((ModalDoubleFacedCard) card).getLeftHalfCard().getSpellAbility().copy();
            } else if (((ModalDoubleFacedCard) card).getRightHalfCard().getName().equals(abilityName)) {
                spellAbilityCopy = ((ModalDoubleFacedCard) card).getRightHalfCard().getSpellAbility().copy();
            } else {
                spellAbilityCopy = null;
            }
        } else {
            spellAbilityCopy = card.getSpellAbility().copy();
        }
        if (spellAbilityCopy == null) {
            return null;
        }
        spellAbilityCopy.setId(this.getId());
        spellAbilityCopy.clearManaCosts();
        spellAbilityCopy.clearManaCostsToPay();
        spellAbilityCopy.addCost(this.getCosts().copy());
        spellAbilityCopy.addCost(this.getManaCosts().copy());
        spellAbilityCopy.setSpellAbilityCastMode(this.getSpellAbilityCastMode());
        spellAbilityCopy.setCostAdjuster(this.getCostAdjuster());
        spellAbilityToResolve = spellAbilityCopy;
        ContinuousEffect effect = new CastFromGraveyardReplacementEffect();
        effect.setTargetPointer(new FixedTarget(getSourceId(), game.getState().getZoneChangeCounter(getSourceId())));
        game.addEffect(effect, this);
        return spellAbilityToResolve;
    }

    @Override
    public Costs<Cost> getCosts() {
        if (spellAbilityToResolve == null) {
            return super.getCosts();
        }
        return spellAbilityToResolve.getCosts();
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    /**
     * Used for split card in PlayerImpl method:
     * getOtherUseableActivatedAbilities
     *
     * @param abilityName
     */
    public CastFromGraveyardAbility setAbilityName(String abilityName) {
        this.abilityName = abilityName;
        return this;
    }
}

class CastFromGraveyardReplacementEffect extends ReplacementEffectImpl {

    public CastFromGraveyardReplacementEffect() {
        super(Duration.OneUse, Outcome.Exile);
    }

    protected CastFromGraveyardReplacementEffect(final CastFromGraveyardReplacementEffect effect) {
        super(effect);
    }

    @Override
    public CastFromGraveyardReplacementEffect copy() {
        return new CastFromGraveyardReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return false;
        }
        discard();
        return controller.moveCards(
                card, Zone.EXILED, source, game, false,
                false, false, event.getAppliedEffects()
        );
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID cardId = CardUtil.getMainCardId(game, source.getSourceId()); // for split cards
        if (!cardId.equals(event.getTargetId())
                || ((ZoneChangeEvent) event).getFromZone() != Zone.STACK
                || ((ZoneChangeEvent) event).getToZone() == Zone.EXILED) {
            return false;
        }
        int zcc = game.getState().getZoneChangeCounter(cardId);
        return ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == zcc;
    }
}
