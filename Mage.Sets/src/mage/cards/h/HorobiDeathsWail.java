package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX
 */
public final class HorobiDeathsWail extends CardImpl {

    public HorobiDeathsWail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature becomes the target of a spell or ability, destroy that creature.
        this.addAbility(new HorobiDeathsWailAbility(new DestroyTargetEffect()));
    }

    private HorobiDeathsWail(final HorobiDeathsWail card) {
        super(card);
    }

    @Override
    public HorobiDeathsWail copy() {
        return new HorobiDeathsWail(this);
    }

}

class HorobiDeathsWailAbility extends TriggeredAbilityImpl {

    public HorobiDeathsWailAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public HorobiDeathsWailAbility(final HorobiDeathsWailAbility ability) {
        super(ability);
    }

    @Override
    public HorobiDeathsWailAbility copy() {
        return new HorobiDeathsWailAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        if (creature != null && creature.isCreature(game)) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature becomes the target of a spell or ability, destroy that creature.";
    }
}
