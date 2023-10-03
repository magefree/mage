
package mage.cards.d;

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
public final class Deadfall extends CardImpl {

    public Deadfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Creatures with forestwalk can be blocked as though they didn't have forestwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DeadfallEffect()));
    }

    private Deadfall(final Deadfall card) {
        super(card);
    }

    @Override
    public Deadfall copy() {
        return new Deadfall(this);
    }
}

class DeadfallEffect extends AsThoughEffectImpl {

    public DeadfallEffect() {
        super(AsThoughEffectType.BLOCK_FORESTWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures with forestwalk can be blocked as though they didn't have forestwalk";
    }

    private DeadfallEffect(final DeadfallEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DeadfallEffect copy() {
        return new DeadfallEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}
