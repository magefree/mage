
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

/**
 *
 * @author L_J
 */
public final class UrDrago extends CardImpl {

    public UrDrago(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Creatures with swampwalk can be blocked as though they didn't have swampwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UrDragoEffect()));
    }

    private UrDrago(final UrDrago card) {
        super(card);
    }

    @Override
    public UrDrago copy() {
        return new UrDrago(this);
    }
}

class UrDragoEffect extends AsThoughEffectImpl {

    public UrDragoEffect() {
        super(AsThoughEffectType.BLOCK_SWAMPWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures with swampwalk can be blocked as though they didn't have swampwalk";
    }

    public UrDragoEffect(final UrDragoEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public UrDragoEffect copy() {
        return new UrDragoEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}
