package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class BlackPantherWakandanKing extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    public BlackPantherWakandanKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Survey the Realm — Whenever Black Panther or another creature you control enters, put a +1/+1 counter on
        // target land you control.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_PERMANENT_CREATURE, false, true).setTriggerPhrase(
                        "Whenever {this} or another creature you control enters, ");
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Survey the Realm"));

        // Mine Vibranium — {3}: Move all +1/+1 counters from target land you control onto target creature. If one or
        // more +1/+1 counters are moved this way, you gain that much life and draw a card.
        ability = new SimpleActivatedAbility(new BlackPantherWakandanKingEffect(), new GenericManaCost(3));
        ability.addTarget(new TargetPermanent(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Mine Vibranium"));
    }

    private BlackPantherWakandanKing(final BlackPantherWakandanKing card) {
        super(card);
    }

    @Override
    public BlackPantherWakandanKing copy() {
        return new BlackPantherWakandanKing(this);
    }
}

class BlackPantherWakandanKingEffect extends OneShotEffect {

    BlackPantherWakandanKingEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "move all +1/+1 counters from target land you control onto target creature. If one or more " +
                "+1/+1 counters are moved this way, you gain that much life and draw a card.";
    }

    private BlackPantherWakandanKingEffect(final BlackPantherWakandanKingEffect effect) {
        super(effect);
    }

    @Override
    public BlackPantherWakandanKingEffect copy() {
        return new BlackPantherWakandanKingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int countersRemoved = 0;
        Permanent landPermanent = game.getPermanent(source.getFirstTarget());
        Permanent creaturePermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());

        if (landPermanent == null || creaturePermanent == null || controller == null) {
            return false;
        }

        int numberCounters = landPermanent.getCounters(game).getCount(CounterType.P1P1);
        if (numberCounters > 0) {
            landPermanent.removeCounters(CounterType.P1P1.getName(), numberCounters, source, game);
            countersRemoved += numberCounters;
        }

        if (countersRemoved > 0) {
            creaturePermanent.addCounters(CounterType.P1P1.createInstance(countersRemoved), source.getControllerId(), source, game);
            controller.gainLife(countersRemoved, game, source);
            controller.drawCards(1, source, game);
        }
        return true;
    }
}
