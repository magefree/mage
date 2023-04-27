package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampagingGeoderm extends CardImpl {

    public RampagingGeoderm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you attack, target attacking creature gets +1/+1 until end of turn. If it's attacking a battle, put a +1/+1 counter on it instead.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new RampagingGeodermEffect(), 1);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private RampagingGeoderm(final RampagingGeoderm card) {
        super(card);
    }

    @Override
    public RampagingGeoderm copy() {
        return new RampagingGeoderm(this);
    }
}

class RampagingGeodermEffect extends OneShotEffect {

    RampagingGeodermEffect() {
        super(Outcome.Benefit);
        staticText = "target attacking creature gets +1/+1 until end of turn. " +
                "If it's attacking a battle, put a +1/+1 counter on it instead";
    }

    private RampagingGeodermEffect(final RampagingGeodermEffect effect) {
        super(effect);
    }

    @Override
    public RampagingGeodermEffect copy() {
        return new RampagingGeodermEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Permanent defender = game.getPermanent(game.getCombat().getDefenderId(permanent.getId()));
        if (defender != null && defender.isBattle(game)) {
            return permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        game.addEffect(new BoostTargetEffect(1, 1).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
