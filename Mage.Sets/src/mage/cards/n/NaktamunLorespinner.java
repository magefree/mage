package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NaktamunLorespinner extends PrepareCard {

    public NaktamunLorespinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}", "Wheel of Fortune", new CardType[]{CardType.SORCERY}, "{2}{R}");

        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, if a player has one or fewer cards in hand, this creature becomes prepared.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new BecomePreparedSourceEffect())
                .withInterveningIf(NaktamunLorespinnerCondition.instance));

        // Wheel of Fortune
        // Sorcery {2}{R}
        // Each player discards their hand, then draws seven cards.
        this.getSpellCard().getSpellAbility().addEffect(new DiscardHandAllEffect());
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardAllEffect(7).setText(", then draws seven cards"));
    }

    private NaktamunLorespinner(final NaktamunLorespinner card) {
        super(card);
    }

    @Override
    public NaktamunLorespinner copy() {
        return new NaktamunLorespinner(this);
    }
}

enum NaktamunLorespinnerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getHand)
                .mapToInt(Set::size)
                .anyMatch(x -> x <= 1);
    }

    @Override
    public String toString() {
        return "a player has one or fewer cards in hand";
    }
}
