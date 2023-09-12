
package mage.cards.i;

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
 * @author LevelX2
 */
public final class IndenturedOaf extends CardImpl {

    public IndenturedOaf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Prevent all damage that Indentured Oaf would deal to red creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IndenturedOafPreventEffectEffect(Duration.WhileOnBattlefield)));
    }

    private IndenturedOaf(final IndenturedOaf card) {
        super(card);
    }

    @Override
    public IndenturedOaf copy() {
        return new IndenturedOaf(this);
    }
}

class IndenturedOafPreventEffectEffect extends PreventionEffectImpl {

    public IndenturedOafPreventEffectEffect(Duration duration) {
        super(duration, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that {this} would deal to red creatures";
    }

    private IndenturedOafPreventEffectEffect(final IndenturedOafPreventEffectEffect effect) {
        super(effect);
    }

    @Override
    public IndenturedOafPreventEffectEffect copy() {
        return new IndenturedOafPreventEffectEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getSourceId().equals(source.getSourceId())) {
                Permanent damageTo = game.getPermanent(event.getTargetId());
                return damageTo != null && damageTo.getColor(game).isRed();
            }
        }
        return false;
    }

}
