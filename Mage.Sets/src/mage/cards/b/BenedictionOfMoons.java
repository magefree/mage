
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.HauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class BenedictionOfMoons extends CardImpl {

    public BenedictionOfMoons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // You gain 1 life for each player.
        this.getSpellAbility().addEffect(new GainLifeEffect(PlayerCount.instance).setText("you gain 1 life for each player"));

        // Haunt
        // When the creature Benediction of Moons haunts dies, you gain 1 life for each player.
        this.addAbility(new HauntAbility(this, new GainLifeEffect(PlayerCount.instance).setText("you gain 1 life for each player")));

    }

    private BenedictionOfMoons(final BenedictionOfMoons card) {
        super(card);
    }

    @Override
    public BenedictionOfMoons copy() {
        return new BenedictionOfMoons(this);
    }
}

enum PlayerCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getNumPlayers();
    }

    @Override
    public PlayerCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "player";
    }
}
