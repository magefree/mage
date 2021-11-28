package mage.cards.a;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelicExaltation extends CardImpl {

    public AngelicExaltation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever a creature you control attacks alone, it gets +X/+X until end of turn, where X is the number of creatures you control.
        this.addAbility(new AngelicExaltationAbility().addHint(CreaturesYouControlHint.instance));
    }

    private AngelicExaltation(final AngelicExaltation card) {
        super(card);
    }

    @Override
    public AngelicExaltation copy() {
        return new AngelicExaltation(this);
    }
}

class AngelicExaltationAbility extends TriggeredAbilityImpl {

    public AngelicExaltationAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(CreaturesYouControlCount.instance, CreaturesYouControlCount.instance, Duration.EndOfTurn, true), false);
    }

    public AngelicExaltationAbility(final AngelicExaltationAbility ability) {
        super(ability);
    }

    @Override
    public AngelicExaltationAbility copy() {
        return new AngelicExaltationAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(this.controllerId)) {
            if (game.getCombat().attacksAlone()) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(game.getCombat().getAttackers().get(0), game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks alone, " +
                "it gets +X/+X until end of turn, " +
                "where X is the number of creatures you control.";
    }

}
