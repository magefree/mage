
package mage.cards.g;

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
public final class GostaDirk extends CardImpl {

    public GostaDirk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Creatures with islandwalk can be blocked as though they didn't have islandwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GostaDirkEffect()));
    }

    private GostaDirk(final GostaDirk card) {
        super(card);
    }

    @Override
    public GostaDirk copy() {
        return new GostaDirk(this);
    }
}

class GostaDirkEffect extends AsThoughEffectImpl {

    public GostaDirkEffect() {
        super(AsThoughEffectType.BLOCK_ISLANDWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures with islandwalk can be blocked as though they didn't have islandwalk";
    }

    public GostaDirkEffect(final GostaDirkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GostaDirkEffect copy() {
        return new GostaDirkEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}
