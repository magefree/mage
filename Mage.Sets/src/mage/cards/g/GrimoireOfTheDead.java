package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTargets;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class GrimoireOfTheDead extends CardImpl {

    public GrimoireOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        addSuperType(SuperType.LEGENDARY);

        // {1}, {tap}, Discard a card: Put a study counter on Grimoire of the Dead.
        Ability ability1 = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.STUDY.createInstance()), new GenericManaCost(1)
        );
        ability1.addCost(new TapSourceCost());
        ability1.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability1);

        // {tap}, Remove three study counters from Grimoire of the Dead and sacrifice it: Put all creature cards from all graveyards onto the battlefield under your control. They're black Zombies in addition to their other colors and types.
        Ability ability2 = new SimpleActivatedAbility(new GrimoireOfTheDeadEffect(), new TapSourceCost());
        ability2.addCost(new CompositeCost(
                new RemoveCountersSourceCost(CounterType.STUDY.createInstance(3)),
                new SacrificeSourceCost(), "Remove three study counters from {this} and sacrifice it"
        ));
        this.addAbility(ability2);
    }

    private GrimoireOfTheDead(final GrimoireOfTheDead card) {
        super(card);
    }

    @Override
    public GrimoireOfTheDead copy() {
        return new GrimoireOfTheDead(this);
    }
}

class GrimoireOfTheDeadEffect extends OneShotEffect {

    public GrimoireOfTheDeadEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put all creature cards in all graveyards onto the battlefield under your control. " +
                "They're black Zombies in addition to their other colors and types";
    }

    public GrimoireOfTheDeadEffect(final GrimoireOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> creatureCards = new LinkedHashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            creatureCards.addAll(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        }
        controller.moveCards(creatureCards, Zone.BATTLEFIELD, source, game);
        game.addEffect(new GrimoireOfTheDeadEffect2().setTargetPointer(new FixedTargets(creatureCards, game)), source);
        return true;
    }

    @Override
    public GrimoireOfTheDeadEffect copy() {
        return new GrimoireOfTheDeadEffect(this);
    }

}

class GrimoireOfTheDeadEffect2 extends ContinuousEffectImpl {

    public GrimoireOfTheDeadEffect2() {
        super(Duration.Custom, Outcome.Neutral);
    }

    public GrimoireOfTheDeadEffect2(final GrimoireOfTheDeadEffect2 effect) {
        super(effect);
    }

    @Override
    public GrimoireOfTheDeadEffect2 copy() {
        return new GrimoireOfTheDeadEffect2(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (UUID permanentId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent == null) {
                continue;
            }
            switch (layer) {
                case ColorChangingEffects_5:
                    permanent.getColor(game).setBlack(true);
                    break;
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.ZOMBIE);
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }
}
