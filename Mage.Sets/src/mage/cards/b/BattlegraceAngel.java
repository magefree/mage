package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public final class BattlegraceAngel extends CardImpl {

    public BattlegraceAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        this.addAbility(new ExaltedAbility());

        // Whenever a creature you control attacks alone, it gains lifelink until end of turn.
        this.addAbility(new BattlegraceAngelAbility());
    }

    public BattlegraceAngel(final BattlegraceAngel card) {
        super(card);
    }

    @Override
    public BattlegraceAngel copy() {
        return new BattlegraceAngel(this);
    }

}

class BattlegraceAngelAbility extends TriggeredAbilityImpl {

    public BattlegraceAngelAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn), false);
    }

    public BattlegraceAngelAbility(final BattlegraceAngelAbility ability) {
        super(ability);
    }

    @Override
    public BattlegraceAngelAbility copy() {
        return new BattlegraceAngelAbility(this);
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
        return "Whenever a creature you control attacks alone, it gains lifelink until end of turn.";
    }

}
