package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.RandomUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StraxSontaranNurse extends CardImpl {

    public StraxSontaranNurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Grenades! -- {2}, {T}, Sacrifice an artifact: Choose a player at random. When you do, Strax fights another target creature that player controls.
        Ability ability = new SimpleActivatedAbility(new StraxSontaranNurseEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_AN));
        this.addAbility(ability.withFlavorWord("Grenades!"));

        // Glory of Battle -- Whenever Strax deals damage to a creature, put a +1/+1 counter on Strax.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, false, false
        ).withFlavorWord("Glory of Battle"));
    }

    private StraxSontaranNurse(final StraxSontaranNurse card) {
        super(card);
    }

    @Override
    public StraxSontaranNurse copy() {
        return new StraxSontaranNurse(this);
    }
}

class StraxSontaranNurseEffect extends OneShotEffect {

    StraxSontaranNurseEffect() {
        super(Outcome.Benefit);
        staticText = "choose a player at random. When you do, " +
                "{this} fights another target creature that player controls";
    }

    private StraxSontaranNurseEffect(final StraxSontaranNurseEffect effect) {
        super(effect);
    }

    @Override
    public StraxSontaranNurseEffect copy() {
        return new StraxSontaranNurseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(RandomUtil.randomFromCollection(
                game.getState().getPlayersInRange(source.getControllerId(), game, true)
        ));
        if (player == null) {
            return false;
        }
        game.informPlayers(player.getLogName() + " has been chosen at random");
        FilterPermanent filter = new FilterCreaturePermanent("another creature controlled by " + player.getName());
        filter.add(AnotherPredicate.instance);
        filter.add(new ControllerIdPredicate(player.getId()));
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new FightTargetSourceEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
