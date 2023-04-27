
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class RoilingHorror extends CardImpl {

    public RoilingHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Roiling Horror's power and toughness are each equal to your life total minus the life total of an opponent with the most life.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new RoilingHorrorDynamicValue())
                .setText("{this}'s power and toughness are each equal to your life total minus the life total of an opponent with the most life.")
        ));

        // Suspend X-{X}{B}{B}{B}. X can't be 0.
        this.addAbility(new SuspendAbility(Integer.MAX_VALUE, new ManaCostsImpl<>("{B}{B}{B}"), this, true));

        // Whenever a time counter is removed from Roiling Horror while it's exiled, target player loses 1 life and you gain 1 life.
        this.addAbility(new RoilingHorrorTriggeredAbility());

    }

    private RoilingHorror(final RoilingHorror card) {
        super(card);
    }

    @Override
    public RoilingHorror copy() {
        return new RoilingHorror(this);
    }
}

class RoilingHorrorTriggeredAbility extends TriggeredAbilityImpl {

    public RoilingHorrorTriggeredAbility() {
        super(Zone.EXILED, new LoseLifeTargetEffect(1), false);
        this.addTarget(new TargetPlayer());
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        this.addEffect(effect);
        setTriggerPhrase("Whenever a time counter is removed from {this} while it's exiled, ");
    }

    public RoilingHorrorTriggeredAbility(final RoilingHorrorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RoilingHorrorTriggeredAbility copy() {
        return new RoilingHorrorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(CounterType.TIME.getName()) && event.getTargetId().equals(this.getSourceId());
    }
}

class RoilingHorrorDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int opponentsMostLife = Integer.MIN_VALUE;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            for (UUID playerUUID : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerUUID, game)) {
                    Player opponent = game.getPlayer(playerUUID);
                    if (opponent != null && opponent.getLife() > opponentsMostLife) {
                        opponentsMostLife = opponent.getLife();
                    }
                }
            }
            return controller.getLife() - opponentsMostLife;
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "your life total minus the life total of an opponent with the most life";
    }
}
