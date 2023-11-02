package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SlumberingDragon extends CardImpl {

    public SlumberingDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Slumbering Dragon can't attack or block unless it has five or more +1/+1 counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SlumberingDragonEffect()));

        // Whenever a creature attacks you or a planeswalker you control, put a +1/+1 counter on Slumbering Dragon.
        this.addAbility(new AttacksAllTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PERMANENT, true));
    }

    private SlumberingDragon(final SlumberingDragon card) {
        super(card);
    }

    @Override
    public SlumberingDragon copy() {
        return new SlumberingDragon(this);
    }
}

class SlumberingDragonEffect extends RestrictionEffect {

    public SlumberingDragonEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless it has five or more +1/+1 counters on it";
    }

    private SlumberingDragonEffect(final SlumberingDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return permanent.getCounters(game).getCount(CounterType.P1P1) < 5;
        }
        // don't apply for all other creatures!
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public SlumberingDragonEffect copy() {
        return new SlumberingDragonEffect(this);
    }
}
