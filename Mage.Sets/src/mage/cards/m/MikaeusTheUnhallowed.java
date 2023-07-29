
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class MikaeusTheUnhallowed extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Human creatures");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public MikaeusTheUnhallowed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(IntimidateAbility.getInstance());
        // Whenever a Human deals damage to you, destroy it.
        this.addAbility(new MikaeusTheUnhallowedAbility());

        // Other non-Human creatures you control get +1/+1 and have undying.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true));
        ability.addEffect(new GainAbilityControlledEffect(new UndyingAbility(), Duration.WhileOnBattlefield, filter, true)
                .setText("and have undying. <i>(When a creature with undying dies, if it had no +1/+1 counters on it," +
                        " return it to the battlefield under its owner's control with a +1/+1 counter on it.)</i>"));
        this.addAbility(ability);
    }

    private MikaeusTheUnhallowed(final MikaeusTheUnhallowed card) {
        super(card);
    }

    @Override
    public MikaeusTheUnhallowed copy() {
        return new MikaeusTheUnhallowed(this);
    }
}

class MikaeusTheUnhallowedAbility extends TriggeredAbilityImpl {

    public MikaeusTheUnhallowedAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
    }

    public MikaeusTheUnhallowedAbility(final MikaeusTheUnhallowedAbility ability) {
        super(ability);
    }

    @Override
    public MikaeusTheUnhallowedAbility copy() {
        return new MikaeusTheUnhallowedAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.controllerId)) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.hasSubtype(SubType.HUMAN, game)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Human deals damage to you, destroy it.";
    }
}
