
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class DauntingDefender extends CardImpl {

    public DauntingDefender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If a source would deal damage to a Cleric creature you control, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DauntingDefenderEffect(1)));
    }

    private DauntingDefender(final DauntingDefender card) {
        super(card);
    }

    @Override
    public DauntingDefender copy() {
        return new DauntingDefender(this);
    }
}

class DauntingDefenderEffect extends PreventionEffectImpl {

    public DauntingDefenderEffect(int amount) {
        super(Duration.WhileOnBattlefield, amount, false, false);
        this.staticText = "If a source would deal damage to a Cleric creature you control, prevent " + amount + " of that damage";
    }

    private DauntingDefenderEffect(final DauntingDefenderEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isControlledBy(source.getControllerId()) && permanent.isCreature(game) && permanent.hasSubtype(SubType.CLERIC, game)) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public DauntingDefenderEffect copy() {
        return new DauntingDefenderEffect(this);
    }
}
