package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Styxo/spjspj
 */
public final class JangoFett extends CardImpl {

    public JangoFett(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Jango Fett attacks, put a bounty counter on target creature an opponent controls.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever Jango Fett attacks, it gets +X/+0, where X is the number of creatures defending player controls with a bounty counter on them
        this.addAbility(new AttacksTriggeredAbility(new JangoFettEffect()));
    }

    private JangoFett(final JangoFett card) {
        super(card);
    }

    @Override
    public JangoFett copy() {
        return new JangoFett(this);
    }
}

class JangoFettEffect extends OneShotEffect {

    public JangoFettEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "it gets +X/+0, where X is the number of creatures defending player controls with a bounty counter on them";
    }

    public JangoFettEffect(final JangoFettEffect ability) {
        super(ability);
    }

    @Override
    public JangoFettEffect copy() {
        return new JangoFettEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getSourceId());
        if (creature == null) {
            return false;
        }

        // Count the number of creatures attacked opponent controls with a bounty counter
        UUID defenderId = game.getCombat().getDefendingPlayerId(creature.getId(), game);
        int count = 0;
        if (defenderId != null) {
            FilterCreaturePermanent bountyFilter = new FilterCreaturePermanent("creatures defending player controls with a bounty counter");
            bountyFilter.add(CounterType.BOUNTY.getPredicate());
            count = game.getBattlefield().countAll(bountyFilter, defenderId, game);
        }

        if (count == 0) {
            return false;
        }

        game.addEffect(new BoostSourceEffect(count, 0, Duration.WhileOnBattlefield), source);
        return true;
    }
}
