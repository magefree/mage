package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NumaJoragaChieftain extends CardImpl {

    public NumaJoragaChieftain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, you may pay {X}{X}. When you do, distribute X +1/+1 counters among any number of target Elves.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new NumaJoragaChieftainEffect(), TargetController.YOU, false
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private NumaJoragaChieftain(final NumaJoragaChieftain card) {
        super(card);
    }

    @Override
    public NumaJoragaChieftain copy() {
        return new NumaJoragaChieftain(this);
    }
}

class NumaJoragaChieftainEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "Elves");

    NumaJoragaChieftainEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}{X}. When you do, distribute X +1/+1 counters among any number of target Elves";
    }

    private NumaJoragaChieftainEffect(final NumaJoragaChieftainEffect effect) {
        super(effect);
    }

    @Override
    public NumaJoragaChieftainEffect copy() {
        return new NumaJoragaChieftainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ManaCosts cost = new ManaCostsImpl<>("{X}{X}");
        if (!player.chooseUse(Outcome.BoostCreature, "Pay " + cost.getText() + "?", source, game)) {
            return false;
        }
        int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
        cost.add(new GenericManaCost(2 * costX));
        if (!cost.pay(source, game, source, source.getControllerId(), false, null)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DistributeCountersEffect(CounterType.P1P1, costX, false, ""),
                false, "distribute " + costX + " +1/+1 counters among any number of target Elves"
        );
        ability.addTarget(new TargetCreaturePermanentAmount(costX, filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
