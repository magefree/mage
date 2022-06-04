package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CommanderCardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cloudkill extends CardImpl {

    public Cloudkill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // All creatures gets -X/-X until end of turn, where X is the greatest mana value of a commander you own on the battlefield or in the command zone.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                CloudkillValue.instance, CloudkillValue.instance, Duration.EndOfTurn
        ));
    }

    private Cloudkill(final Cloudkill card) {
        super(card);
    }

    @Override
    public Cloudkill copy() {
        return new Cloudkill(this);
    }
}

enum CloudkillValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        return player != null ? -game
                .getCommanderCardsFromAnyZones(
                        player, CommanderCardType.ANY,
                        Zone.BATTLEFIELD, Zone.COMMAND
                )
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0) : 0;
    }

    @Override
    public CloudkillValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the greatest mana value of a commander you own on the battlefield or in the command zone";
    }

    @Override
    public String toString() {
        return "-X";
    }
}
