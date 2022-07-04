package mage.cards.x;

import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReplaceTreasureWithAdditionalEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author weirddan455
 */
public final class Xorn extends CardImpl {

    public Xorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // If you would create one or more Treasure tokens, instead create those tokens plus an additional Treasure token.
        this.addAbility(new SimpleStaticAbility(new ReplaceTreasureWithAdditionalEffect()));
    }

    private Xorn(final Xorn card) {
        super(card);
    }

    @Override
    public Xorn copy() {
        return new Xorn(this);
    }
}