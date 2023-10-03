
package mage.cards.s;

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
public final class StaffOfTheAges extends CardImpl {

    public StaffOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Creatures with landwalk abilities can be blocked as though they didn't have those abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StaffOfTheAgesEffect()));
    }

    private StaffOfTheAges(final StaffOfTheAges card) {
        super(card);
    }

    @Override
    public StaffOfTheAges copy() {
        return new StaffOfTheAges(this);
    }
}

class StaffOfTheAgesEffect extends AsThoughEffectImpl {

    public StaffOfTheAgesEffect() {
        super(AsThoughEffectType.BLOCK_LANDWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures with landwalk abilities can be blocked as though they didn't have those abilities";
    }

    private StaffOfTheAgesEffect(final StaffOfTheAgesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public StaffOfTheAgesEffect copy() {
        return new StaffOfTheAgesEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}
