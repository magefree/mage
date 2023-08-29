
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author L_J
 */
public final class Undertow extends CardImpl {

    public Undertow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Creatures with islandwalk can be blocked as though they didn't have islandwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UndertowEffect()));
    }

    private Undertow(final Undertow card) {
        super(card);
    }

    @Override
    public Undertow copy() {
        return new Undertow(this);
    }
}

class UndertowEffect extends AsThoughEffectImpl {

    public UndertowEffect() {
        super(AsThoughEffectType.BLOCK_ISLANDWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures with islandwalk can be blocked as though they didn't have islandwalk";
    }

    private UndertowEffect(final UndertowEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public UndertowEffect copy() {
        return new UndertowEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}
