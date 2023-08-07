
package mage.cards.g;

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
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2 & L_J
 */
public final class GoblinFurrier extends CardImpl {

    public GoblinFurrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prevent all damage that Goblin Furrier would deal to snow creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GoblinFurrierPreventEffectEffect(Duration.WhileOnBattlefield)));
    }

    private GoblinFurrier(final GoblinFurrier card) {
        super(card);
    }

    @Override
    public GoblinFurrier copy() {
        return new GoblinFurrier(this);
    }
}

class GoblinFurrierPreventEffectEffect extends PreventionEffectImpl {

    public GoblinFurrierPreventEffectEffect(Duration duration) {
        super(duration, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that {this} would deal to snow creatures";
    }

    public GoblinFurrierPreventEffectEffect(final GoblinFurrierPreventEffectEffect effect) {
        super(effect);
    }

    @Override
    public GoblinFurrierPreventEffectEffect copy() {
        return new GoblinFurrierPreventEffectEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getSourceId().equals(source.getSourceId())) {
                Permanent damageTo = game.getPermanent(event.getTargetId());
                return damageTo != null && damageTo.isSnow(game);
            }
        }
        return false;
    }

}
