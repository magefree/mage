package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class RafiqOfTheMany extends CardImpl {

    public RafiqOfTheMany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        this.addAbility(new ExaltedAbility());

        // Whenever a creature you control attacks alone, it gains double strike until end of turn.
        this.addAbility(new RafiqOfTheManyAbility());
    }

    private RafiqOfTheMany(final RafiqOfTheMany card) {
        super(card);
    }

    @Override
    public RafiqOfTheMany copy() {
        return new RafiqOfTheMany(this);
    }

}

class RafiqOfTheManyAbility extends TriggeredAbilityImpl {

    public RafiqOfTheManyAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
    }

    public RafiqOfTheManyAbility(final RafiqOfTheManyAbility ability) {
        super(ability);
    }

    @Override
    public RafiqOfTheManyAbility copy() {
        return new RafiqOfTheManyAbility(this);
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
        return "Whenever a creature you control attacks alone, it gains double strike until end of turn.";
    }

}
