package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.costadjusters.CompositeCostAdjuster;
import mage.abilities.costs.mana.ManaCost;
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
    private final CostAdjuster cardCostAdjuster;
    private final Costs<Cost> ownCosts; // without the card's own costs, for the rules text
    private boolean replacementEffectAdded = false;

    protected CastFromGraveyardAbility(Card card, Cost cost, SpellAbilityCastMode spellAbilityCastMode) {
        // same as jump-start or escape: it's the card's spell ability with another mana cost, so the card's own costs
        // and cost adjuster are part of it from the start (playable and AI must see the same costs as the real cast)
        super(card.getSpellAbility() != null
                ? card.getSpellAbility()
                : new SpellAbility(null, "", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE, spellAbilityCastMode));
        this.newId();
        this.zone = Zone.GRAVEYARD;
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.spellAbilityCastMode = spellAbilityCastMode;
        this.cardCostAdjuster = this.getCostAdjuster();
        this.ownCosts = new CostsImpl<>();
        this.setAdditionalCostsRuleVisible(false);
        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.name = spellAbilityCastMode + " " + cost.getText();
        this.addCost(cost);
        this.timing = card.isSorcery() ? TimingRule.SORCERY : TimingRule.INSTANT;
    }

    protected CastFromGraveyardAbility(final CastFromGraveyardAbility ability) {
        super(ability);
        this.abilityName = ability.abilityName;
        this.cardCostAdjuster = ability.cardCostAdjuster;
        this.ownCosts = ability.ownCosts.copy();
        this.replacementEffectAdded = ability.replacementEffectAdded;
    }

    @Override
    public void addCost(Cost cost, int index) {
        super.addCost(cost, index);
        if (cost != null && !(cost instanceof Costs) && !(cost instanceof ManaCost)) {
            ownCosts.add(cost.copy());
        }
    }

    /**
     * Non-mana costs of this ability, without the card's own additional costs
     */
    protected Costs<Cost> getOwnCosts() {
        return ownCosts;
    }

    @Override
    public CastFromGraveyardAbility setCostAdjuster(CostAdjuster costAdjuster) {
        // the card's own cost adjuster still applies (e.g. Hurkyl's Final Meditation cast with harmonize)
        super.setCostAdjuster(CompositeCostAdjuster.of(cardCostAdjuster, costAdjuster));
        return this;
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
        if (!replacementEffectAdded) {
            replacementEffectAdded = true;
            ContinuousEffect effect = new CastFromGraveyardReplacementEffect();
            effect.setTargetPointer(new FixedTarget(getSourceId(), game.getState().getZoneChangeCounter(getSourceId())));
            game.addEffect(effect, this);
        }
        return this;
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
