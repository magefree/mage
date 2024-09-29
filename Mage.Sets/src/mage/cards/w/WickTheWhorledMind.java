package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SnailToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class WickTheWhorledMind extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.RAT, "Rat");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.SNAIL, "Snail");

    public WickTheWhorledMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT, SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Wick or another Rat you control enters, create a 1/1 black Snail creature token if you don't control a Snail. Otherwise, put a +1/+1 counter on a Snail you control.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new WickTheWhorledMindEffect(), filter, false, true
        ));

        // {U}{B}{R}, Sacrifice a Snail: Wick deals damage equal to the sacrificed creature's power to each opponent. Then draw cards equal to the sacrificed creature's power.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(SacrificeCostCreaturesPower.instance, TargetController.OPPONENT)
                .setText("{this} deals damage equal to the sacrificed creature's power to each opponent"), new ManaCostsImpl<>("{U}{B}{R}")
        );
        ability.addEffect(new DrawCardSourceControllerEffect(SacrificeCostCreaturesPower.instance)
                .setText("Then draw cards equal to the sacrificed creature's power")
        );
        ability.addCost(new SacrificeTargetCost(filter2));
        this.addAbility(ability);
    }

    private WickTheWhorledMind(final WickTheWhorledMind card) {
        super(card);
    }

    @Override
    public WickTheWhorledMind copy() {
        return new WickTheWhorledMind(this);
    }
}

class WickTheWhorledMindEffect extends OneShotEffect {

    WickTheWhorledMindEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 black Snail creature token if you don't control a Snail. Otherwise, put a +1/+1 counter on a Snail you control";
    }

    private WickTheWhorledMindEffect(final WickTheWhorledMindEffect effect) {
        super(effect);
    }

    @Override
    public WickTheWhorledMindEffect copy() {
        return new WickTheWhorledMindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterControlledPermanent filter = new FilterControlledPermanent(SubType.SNAIL, "Snail");

        if (game.getBattlefield().contains(filter, source.getControllerId(), source, game, 1)) {
            TargetPermanent target = new TargetPermanent(filter);
            target.withNotTarget(true);
            if (!target.canChoose(source.getControllerId(), source, game)) {
                return false;
            }
            controller.choose(Outcome.BoostCreature, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                return true;
            }
            return false;
        }
        return new SnailToken().putOntoBattlefield(1, game, source, source.getControllerId());
    }
}
