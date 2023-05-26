package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CrovaxTheCursed extends CardImpl {

    public CrovaxTheCursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Crovax the Cursed enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)), "with four +1/+1 counters on it"));

        // At the beginning of your upkeep, you may sacrifice a creature. If you do, put a +1/+1 counter on Crovax. If you don't, remove a +1/+1 counter from Crovax.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new CrovaxTheCursedEffect(), TargetController.YOU, false);
        this.addAbility(ability);

        // {B}: Crovax gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));

    }

    private CrovaxTheCursed(final CrovaxTheCursed card) {
        super(card);
    }

    @Override
    public CrovaxTheCursed copy() {
        return new CrovaxTheCursed(this);
    }
}

class CrovaxTheCursedEffect extends OneShotEffect {

    public CrovaxTheCursedEffect() {
        super(Outcome.Detriment);
        this.staticText = "you may sacrifice a creature. If you do, put a +1/+1 counter on {this}. If you don't, remove a +1/+1 counter from {this}";
    }

    public CrovaxTheCursedEffect(final CrovaxTheCursedEffect effect) {
        super(effect);
    }

    @Override
    public CrovaxTheCursedEffect copy() {
        return new CrovaxTheCursedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
            int creatures = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), game);
            if (creatures > 0 && controller.chooseUse(outcome, "Sacrifice a creature?", source, game)) {
                if (new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURES, 1, "").apply(game, source)) {
                    if (sourceObject != null) {
                        sourceObject.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                        game.informPlayers(controller.getLogName() + " puts a +1/+1 counter on " + sourceObject.getName());
                    }
                }
            } else if (sourceObject != null && sourceObject.getCounters(game).containsKey(CounterType.P1P1)) {
                sourceObject.removeCounters(CounterType.P1P1.getName(), 1, source, game);
                game.informPlayers(controller.getLogName() + " removes a +1/+1 counter from " + sourceObject.getName());
            }
            return true;
        }
        return false;
    }
}
