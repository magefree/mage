package mage.abilities.keyword;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.ModalDoubleFacedCard;
import mage.cards.SplitCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class HarmonizeAbility extends SpellAbility {

    private String abilityName;
    private SpellAbility spellAbilityToResolve;

    public HarmonizeAbility(Card card, String manaString) {
        super(null, "", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE, SpellAbilityCastMode.HARMONIZE);
        this.setAdditionalCostsRuleVisible(false);
        this.name = "Harmonize " + manaString;
        this.addCost(new ManaCostsImpl<>(manaString));
        this.addCost(new HarmonizeCost());
        this.addSubAbility(new SimpleStaticAbility(Zone.ALL, new HarmonizeCostReductionEffect()).setRuleVisible(false));
        this.timing = card.isSorcery() ? TimingRule.SORCERY : TimingRule.INSTANT;
    }

    private HarmonizeAbility(final HarmonizeAbility ability) {
        super(ability);
    }

    @Override
    public HarmonizeAbility copy() {
        return new HarmonizeAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // harmonize ability dynamicly added to all card's parts (split cards)
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
        // Cards with no Mana Costs cant't be harmonized (e.g. Ancestral Vision)
        if (card.getManaCost().isEmpty()) {
            return ActivationStatus.getFalse();
        }
        // Harmonize can never cast a split card by Fuse, because Fuse only works from hand
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
        if (card == null) {
            return spellAbilityToResolve;
        }
        if (spellAbilityToResolve != null) {
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
        spellAbilityToResolve = spellAbilityCopy;
        ContinuousEffect effect = new HarmonizeReplacementEffect();
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
    public String getRule() {
        return name;
    }
}

class HarmonizeReplacementEffect extends ReplacementEffectImpl {

    public HarmonizeReplacementEffect() {
        super(Duration.OneUse, Outcome.Exile);
    }

    protected HarmonizeReplacementEffect(final HarmonizeReplacementEffect effect) {
        super(effect);
    }

    @Override
    public HarmonizeReplacementEffect copy() {
        return new HarmonizeReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(event.getTargetId());
        if (controller == null || card == null) {
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
        return event.getTargetId().equals(cardId)
                && ((ZoneChangeEvent) event).getFromZone().match(Zone.STACK)
                && !((ZoneChangeEvent) event).getToZone().match(Zone.EXILED)
                && ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(cardId);
    }
}

class HarmonizeCostReductionEffect extends CostModificationEffectImpl {

    HarmonizeCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    private HarmonizeCostReductionEffect(final HarmonizeCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (game.inCheckPlayableState()) {
            return true;
        }
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        int power = CardUtil
                .castStream(spellAbility.getCosts().stream(), HarmonizeCost.class)
                .map(HarmonizeCost::getChosenCreature)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .map(x -> Math.max(x, 0))
                .sum();
        if (power > 0) {
            CardUtil.adjustCost(spellAbility, power);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public HarmonizeCostReductionEffect copy() {
        return new HarmonizeCostReductionEffect(this);
    }
}

class HarmonizeCost extends VariableCostImpl {

    private UUID chosenCreature = null;

    HarmonizeCost() {
        super(VariableCostType.ADDITIONAL, "", "");
    }

    private HarmonizeCost(final HarmonizeCost cost) {
        super(cost);
        this.chosenCreature = cost.chosenCreature;
    }

    @Override
    public HarmonizeCost copy() {
        return new HarmonizeCost(this);
    }

    @Override
    public void clearPaid() {
        super.clearPaid();
        chosenCreature = null;
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE, source, game, 1) ? 1 : 0;
    }

    @Override
    public int announceXValue(Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE, source, game, 1
        )) {
            return 0;
        }
        TargetPermanent target = new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE, true
        );
        target.withChooseHint("for harmonize");
        player.choose(Outcome.PlayForFree, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return 0;
        }
        chosenCreature = permanent.getId();
        return 1;
    }

    private FilterControlledPermanent makeFilter() {
        FilterControlledPermanent filter = new FilterControlledPermanent("tap the chosen creature");
        filter.add(new PermanentIdPredicate(chosenCreature));
        return filter;
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new TapTargetCost(new TargetControlledPermanent(xValue, xValue, makeFilter(), true));
    }

    public UUID getChosenCreature() {
        return chosenCreature;
    }
}
