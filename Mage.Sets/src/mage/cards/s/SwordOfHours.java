package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.DealsCombatDamageEquippedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwordOfHours extends CardImpl {

    public SwordOfHours(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, put a +1/+1 counter on it.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), "it")
        ));

        // Whenever equipped creature deals combat damage, roll a d12. If the result is greater than the damage dealt or the result is 12, double the number of +1/+1 counters on that creature.
        this.addAbility(new DealsCombatDamageEquippedTriggeredAbility(new SwordOfHoursEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private SwordOfHours(final SwordOfHours card) {
        super(card);
    }

    @Override
    public SwordOfHours copy() {
        return new SwordOfHours(this);
    }
}

class SwordOfHoursEffect extends OneShotEffect {

    SwordOfHoursEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d12. If the result is greater than the damage dealt " +
                "or the result is 12, double the number of +1/+1 counters on that creature";
    }

    private SwordOfHoursEffect(final SwordOfHoursEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfHoursEffect copy() {
        return new SwordOfHoursEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 12);
        int damage = (Integer) getValue("damage");
        if (result != 12 && damage <= result) {
            return true;
        }
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return true;
        }
        Permanent permanent = game.getPermanent(sourcePermanent.getAttachedTo());
        if (permanent == null) {
            return true;
        }
        permanent.addCounters(
                CounterType.P1P1.createInstance(
                        permanent.getCounters(game).getCount(CounterType.P1P1)
                ), source.getControllerId(), source, game
        );
        return true;
    }
}
