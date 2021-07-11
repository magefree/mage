
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
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
 * @author MarcoMarin
 */
public final class UncleIstvan extends CardImpl {

    public UncleIstvan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Prevent all damage that would be dealt to Uncle Istvan by creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageToSourceByCardTypeEffect()));
    }

    private UncleIstvan(final UncleIstvan card) {
        super(card);
    }

    @Override
    public UncleIstvan copy() {
        return new UncleIstvan(this);
    }
}

class PreventDamageToSourceByCardTypeEffect extends PreventAllDamageToSourceEffect {

    public PreventDamageToSourceByCardTypeEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Prevent all damage that would be dealt to {this} by creatures";
    }

    public PreventDamageToSourceByCardTypeEffect(final PreventDamageToSourceByCardTypeEffect effect) {
        super(effect.duration);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            MageObject sourceObject = game.getObject(event.getSourceId());
            if (sourceObject != null && sourceObject.isCreature(game)) {
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public PreventAllDamageToSourceEffect copy() {
        return new PreventAllDamageToSourceEffect(this);
    }

}
