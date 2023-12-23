
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
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
 * @author LoneFox

 */
public final class CallousGiant extends CardImpl {

    public CallousGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // If a source would deal 3 or less damage to Callous Giant, prevent that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CallousGiantEffect()));
    }

    private CallousGiant(final CallousGiant card) {
        super(card);
    }

    @Override
    public CallousGiant copy() {
        return new CallousGiant(this);
    }
}

class CallousGiantEffect extends PreventionEffectImpl {

    public CallousGiantEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If a source would deal 3 or less damage to {this}, prevent that damage.";
    }

    private CallousGiantEffect(final CallousGiantEffect effect) {
        super(effect);
    }

    @Override
    public CallousGiantEffect copy() {
        return new CallousGiantEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if(event.getAmount() <= 3)
        {
            preventDamageAction(event, source, game);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId());
    }

}
