
package mage.cards.e;

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
 * @author L_J
 */
public final class EverdawnChampion extends CardImpl {

    public EverdawnChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prevent all combat damage that would be dealt to Everdawn Champion.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EverdawnChampionEffect()));
    }

    private EverdawnChampion(final EverdawnChampion card) {
        super(card);
    }

    @Override
    public EverdawnChampion copy() {
        return new EverdawnChampion(this);
    }
}

class EverdawnChampionEffect extends PreventionEffectImpl {

    public EverdawnChampionEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, true);
        staticText = "Prevent all combat damage that would be dealt to {this}";
    }

    public EverdawnChampionEffect(final EverdawnChampionEffect effect) {
        super(effect);
    }

    @Override
    public EverdawnChampionEffect copy() {
        return new EverdawnChampionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

}
