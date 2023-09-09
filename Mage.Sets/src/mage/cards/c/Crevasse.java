
package mage.cards.c;

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
public final class Crevasse extends CardImpl {

    public Crevasse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Creatures with mountainwalk can be blocked as though they didn't have mountainwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CrevasseEffect()));
    }

    private Crevasse(final Crevasse card) {
        super(card);
    }

    @Override
    public Crevasse copy() {
        return new Crevasse(this);
    }
}

class CrevasseEffect extends AsThoughEffectImpl {

    public CrevasseEffect() {
        super(AsThoughEffectType.BLOCK_MOUNTAINWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures with mountainwalk can be blocked as though they didn't have mountainwalk";
    }

    private CrevasseEffect(final CrevasseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CrevasseEffect copy() {
        return new CrevasseEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}
