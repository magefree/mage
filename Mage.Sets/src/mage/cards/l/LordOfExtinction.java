
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class LordOfExtinction extends CardImpl {

    public LordOfExtinction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Lord of Extinction's power and toughness are each equal to the number of cards in all graveyards.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new LordOfExtinctionDynamicCount(), Duration.EndOfGame)));
    }

    private LordOfExtinction(final LordOfExtinction card) {
        super(card);
    }

    @Override
    public LordOfExtinction copy() {
        return new LordOfExtinction(this);
    }
}

class LordOfExtinctionDynamicCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (UUID playerId : game.getState().getPlayersInRange(sourceAbility.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                count += player.getGraveyard().size();
            }
        }
        return count;
    }

    @Override
    public LordOfExtinctionDynamicCount copy() {
        return new LordOfExtinctionDynamicCount();
    }

    @Override
    public String getMessage() {
        return "cards in all graveyards";
    }

    @Override
    public String toString() {
        return "1";
    }
}
