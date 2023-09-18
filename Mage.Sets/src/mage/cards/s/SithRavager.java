
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Styxo
 */
public final class SithRavager extends CardImpl {

    public SithRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // <i>Hate</i> &mdash; Whenever an opponent loses life from a source other than combat damage, Sith Ravager gets +1/+0 and gains haste and trample until end of turn.
        this.addAbility(new LostNonCombatLifeTriggeredAbility());
    }

    private SithRavager(final SithRavager card) {
        super(card);
    }

    @Override
    public SithRavager copy() {
        return new SithRavager(this);
    }

    public static class LostNonCombatLifeTriggeredAbility extends TriggeredAbilityImpl {

        public LostNonCombatLifeTriggeredAbility() {
            super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), false);
            addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
            addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        }

        private LostNonCombatLifeTriggeredAbility(final LostNonCombatLifeTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public LostNonCombatLifeTriggeredAbility copy() {
            return new LostNonCombatLifeTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.LOST_LIFE;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            // non combat lose life
            return !event.getFlag() && game.getOpponents(game.getControllerId(getSourceId())).contains(event.getPlayerId());
        }

        @Override
        public String getRule() {
            return "<i>Hate</i> &mdash; Whenever an opponent loses life from a source other than combat damage, {this} gains haste and trample until end of turn.";
        }

    }
}
