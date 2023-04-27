
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public final class CrazedArmodon extends CardImpl {

    public CrazedArmodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {G}: Crazed Armodon gets +3/+0 and gains trample until end of turn. Destroy Crazed Armodon at the beginning of the next end step. Activate this ability only once each turn.
        Effect effect = new BoostSourceEffect(3, 0, Duration.EndOfTurn);
        effect.setText("{this} gets +3/+0");
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{G}"));
        effect= new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        ability.addEffect(effect);
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CrazedArmodonDelayedTriggeredAbility()));
        this.addAbility(ability);
    }

    private CrazedArmodon(final CrazedArmodon card) {
        super(card);
    }

    @Override
    public CrazedArmodon copy() {
        return new CrazedArmodon(this);
    }

    static class CrazedArmodonDelayedTriggeredAbility extends DelayedTriggeredAbility {

        public CrazedArmodonDelayedTriggeredAbility() {
            super(new DestroySourceEffect());
        }

        public CrazedArmodonDelayedTriggeredAbility(final CrazedArmodonDelayedTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public CrazedArmodonDelayedTriggeredAbility copy() {
            return new CrazedArmodonDelayedTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return true;
        }

        @Override
        public String getRule() {
            return "Destroy {this} at the beginning of the next end step";
        }
    }
}
