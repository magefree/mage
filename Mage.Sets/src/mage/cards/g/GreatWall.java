
package mage.cards.g;

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
public final class GreatWall extends CardImpl {

    public GreatWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Creatures with plainswalk can be blocked as though they didn't have plainswalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GreatWallEffect()));
    }

    private GreatWall(final GreatWall card) {
        super(card);
    }

    @Override
    public GreatWall copy() {
        return new GreatWall(this);
    }
}

class GreatWallEffect extends AsThoughEffectImpl {

    public GreatWallEffect() {
        super(AsThoughEffectType.BLOCK_PLAINSWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures with plainswalk can be blocked as though they didn't have plainswalk";
    }

    private GreatWallEffect(final GreatWallEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GreatWallEffect copy() {
        return new GreatWallEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}
