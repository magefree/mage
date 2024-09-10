package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrightPalmSoulAwakener extends CardImpl {

    public BrightPalmSoulAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Whenever this creature attacks, double the number of +1/+1 counters on target creature. That creature can't be blocked by creatures with power 2 or less this turn.
        Ability ability = new AttacksTriggeredAbility(new BrightPalmSoulAwakenerEffect())
                .setTriggerPhrase("Whenever this creature attacks, ");
        ability.addEffect(new CantBeBlockedTargetEffect(
                DauntAbility.getFilter(), Duration.EndOfTurn
        ).setText("that creature can't be blocked by creatures with power 2 or less this turn"));
        ability.addTarget(new TargetCreaturePermanent());
        backupAbility.addAbility(ability);
    }

    private BrightPalmSoulAwakener(final BrightPalmSoulAwakener card) {
        super(card);
    }

    @Override
    public BrightPalmSoulAwakener copy() {
        return new BrightPalmSoulAwakener(this);
    }
}

class BrightPalmSoulAwakenerEffect extends OneShotEffect {

    BrightPalmSoulAwakenerEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of +1/+1 counters on target creature";
    }

    private BrightPalmSoulAwakenerEffect(final BrightPalmSoulAwakenerEffect effect) {
        super(effect);
    }

    @Override
    public BrightPalmSoulAwakenerEffect copy() {
        return new BrightPalmSoulAwakenerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int count = permanent.getCounters(game).getCount(CounterType.P1P1);
        return count > 0 && permanent.addCounters(CounterType.P1P1.createInstance(count), source, game);
    }
}
