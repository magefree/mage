package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

public class GlimpseTheCosmos extends CardImpl {

    public GlimpseTheCosmos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(3), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.LIBRARY, false,
                false, false, Zone.HAND, false
        ).setText("look at the top three cards of your library. " +
                "Put one of them into your hand and the rest on the bottom of your library in any order"));

        //As long as you control a Giant, you may cast Glimpse the Cosmos from your graveyard by paying {U} rather than paying its mana cost. If you cast Glimpse the Cosmos this way and it would be put into your graveyard, exile it instead.
        this.addAbility(new GlimpseTheCosmosAbility(new ManaCostsImpl("{U}")));
    }

    private GlimpseTheCosmos(final GlimpseTheCosmos card) {
        super(card);
    }

    @Override
    public GlimpseTheCosmos copy() {
        return new GlimpseTheCosmos(this);
    }

}

class GlimpseTheCosmosAbility extends SpellAbility {

    private String abilityName;
    private SpellAbility spellAbilityToResolve;

    public GlimpseTheCosmosAbility(Cost cost) {
        super(null, "", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE, SpellAbilityCastMode.NORMAL);
        this.setAdditionalCostsRuleVisible(false);
        this.addCost(cost);
        this.timing = TimingRule.SORCERY;
    }

    public GlimpseTheCosmosAbility(final GlimpseTheCosmosAbility ability) {
        super(ability);
        this.spellAbilityType = ability.spellAbilityType;
        this.abilityName = ability.abilityName;
        this.spellAbilityToResolve = ability.spellAbilityToResolve;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Card card = game.getCard(getSourceId());
        if (card != null) {
            // Card must be in the graveyard zone
            if (game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
                return ActivationStatus.getFalse();
            }

            //Must control a giant
            Condition controlGiantCondition = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.GIANT, "you control a Giant"));
            if (!controlGiantCondition.apply(game, this)) {
                return ActivationStatus.getFalse();
            }

            return card.getSpellAbility().canActivate(playerId, game);
        }

        return ActivationStatus.getFalse();
    }

    @Override
    public SpellAbility getSpellAbilityToResolve(Game game) {
        Card card = game.getCard(getSourceId());
        if (card != null) {
            if (spellAbilityToResolve == null) {
                SpellAbility spellAbilityCopy = card.getSpellAbility().copy();
                spellAbilityCopy.setId(this.getId());
                spellAbilityCopy.getManaCosts().clear();
                spellAbilityCopy.getManaCostsToPay().clear();
                spellAbilityCopy.getCosts().addAll(this.getCosts().copy());
                spellAbilityCopy.addCost(this.getManaCosts().copy());
                spellAbilityCopy.setSpellAbilityCastMode(this.getSpellAbilityCastMode());
                spellAbilityToResolve = spellAbilityCopy;
                ContinuousEffect effect = new GlimpseTheCosmosReplacementEffect();
                effect.setTargetPointer(new FixedTarget(getSourceId(), game.getState().getZoneChangeCounter(getSourceId())));
                game.addEffect(effect, this);
            }
        }
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
    public GlimpseTheCosmosAbility copy() {
        return new GlimpseTheCosmosAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        return "As long as you control a Giant, you may cast Glimpse the Cosmos from your graveyard by paying {U} rather than paying its mana cost. If you cast Glimpse the Cosmos this way and it would be put into your graveyard, exile it instead.";
    }

}

class GlimpseTheCosmosReplacementEffect extends ReplacementEffectImpl {

    public GlimpseTheCosmosReplacementEffect() {
        super(Duration.OneUse, Outcome.Exile);
        staticText = "If you cast Glimpse the Cosmos this way and it would be put into your graveyard, exile it instead";
    }

    public GlimpseTheCosmosReplacementEffect(final GlimpseTheCosmosReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GlimpseTheCosmosReplacementEffect copy() {
        return new GlimpseTheCosmosReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                discard();
                return controller.moveCards(
                        card, Zone.EXILED, source, game, false, false, false, event.getAppliedEffects());
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.STACK
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED) {
            return game.getState().getZoneChangeCounter(source.getSourceId()) == source.getSourceObjectZoneChangeCounter() + 1;
        }
        return false;
    }
}
