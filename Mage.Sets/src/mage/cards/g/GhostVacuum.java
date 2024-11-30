package mage.cards.g;

import java.util.Objects;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.AddCreatureTypeAdditionEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author Cguy7777
 */
public final class GhostVacuum extends CardImpl {

    public GhostVacuum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {T}: Exile target card from a graveyard.
        Ability exileAbility
                = new SimpleActivatedAbility(new ExileTargetEffect().setToSourceExileZone(true), new TapSourceCost());
        exileAbility.addTarget(new TargetCardInGraveyard());
        this.addAbility(exileAbility);

        // {6}, {T}, Sacrifice Ghost Vacuum:
        // Put each creature card exiled with Ghost Vacuum onto the battlefield under your control with a flying counter on it.
        // Each of them is a 1/1 Spirit in addition to its other types. Activate only as a sorcery.
        Ability putOntoBattlefieldAbility
                = new ActivateAsSorceryActivatedAbility(new GhostVacuumEffect(), new GenericManaCost(6));
        putOntoBattlefieldAbility.addCost(new TapSourceCost());
        putOntoBattlefieldAbility.addCost(new SacrificeSourceCost());
        this.addAbility(putOntoBattlefieldAbility);
    }

    private GhostVacuum(final GhostVacuum card) {
        super(card);
    }

    @Override
    public GhostVacuum copy() {
        return new GhostVacuum(this);
    }
}

class GhostVacuumEffect extends OneShotEffect {

    GhostVacuumEffect() {
        super(Outcome.Benefit);
        staticText = "Put each creature card exiled with {this} onto the battlefield under your control " +
                "with a flying counter on it. Each of them is a 1/1 Spirit in addition to its other types.";
    }

    private GhostVacuumEffect(final GhostVacuumEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));

        if (player == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }

        // Put each creature card exiled with Ghost Vacuum
        // onto the battlefield under your control with a flying counter on it.
        Cards creatureCards = new CardsImpl(exileZone.getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        Counters countersToAdd = new Counters();
        countersToAdd.addCounter(CounterType.FLYING.createInstance());
        for (Card card : creatureCards.getCards(game)) {
            game.setEnterWithCounters(card.getId(), countersToAdd.copy());
        }
        player.moveCards(creatureCards, Zone.BATTLEFIELD, source, game);

        creatureCards.stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .forEach(permanent -> {
                    // Each of them is a 1/1 Spirit in addition to its other types.
                    ContinuousEffect effect = new SetBasePowerToughnessTargetEffect(1, 1, Duration.EndOfGame);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);

                    effect = new AddCreatureTypeAdditionEffect(SubType.SPIRIT, false);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                });
        return true;
    }

    @Override
    public GhostVacuumEffect copy() {
        return new GhostVacuumEffect(this);
    }
}
