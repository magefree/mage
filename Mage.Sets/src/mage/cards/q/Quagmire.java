
package mage.cards.q;

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
public final class Quagmire extends CardImpl {

    public Quagmire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Creatures with swampwalk can be blocked as though they didn't have swampwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new QuagmireEffect()));
    }

    private Quagmire(final Quagmire card) {
        super(card);
    }

    @Override
    public Quagmire copy() {
        return new Quagmire(this);
    }
}

class QuagmireEffect extends AsThoughEffectImpl {

    public QuagmireEffect() {
        super(AsThoughEffectType.BLOCK_SWAMPWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures with swampwalk can be blocked as though they didn't have swampwalk";
    }

    private QuagmireEffect(final QuagmireEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public QuagmireEffect copy() {
        return new QuagmireEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}
