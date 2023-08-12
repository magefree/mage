
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author noxx
 */
public final class Malignus extends CardImpl {

    public Malignus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Malignus's power and toughness are each equal to half the highest life total among your opponents, rounded up.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new HighestLifeTotalAmongOpponentsCount())));

        // Damage that would be dealt by Malignus can't be prevented.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MalignusEffect()));
    }

    private Malignus(final Malignus card) {
        super(card);
    }

    @Override
    public Malignus copy() {
        return new Malignus(this);
    }
}

class HighestLifeTotalAmongOpponentsCount implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            Player controller = game.getPlayer(sourceAbility.getControllerId());
            if (controller != null) {
                int max = 0;
                for (UUID uuid : game.getOpponents(controller.getId())) {
                    Player opponent = game.getPlayer(uuid);
                    if (opponent != null) {
                        if (opponent.getLife() > max) {
                            max = opponent.getLife();
                        }
                    }
                }
                return (int)Math.ceil(max / 2.0);
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return CardsInControllerHandCount.instance;
    }

    @Override
    public String getMessage() {
        return "half the highest life total among your opponents, rounded up";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class MalignusEffect extends ContinuousRuleModifyingEffectImpl {

    public MalignusEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Damage that would be dealt by {this} can't be prevented";
    }

    public MalignusEffect(final MalignusEffect effect) {
        super(effect);
    }

    @Override
    public MalignusEffect copy() {
        return new MalignusEffect(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(source.getSourceId());
    }

}
