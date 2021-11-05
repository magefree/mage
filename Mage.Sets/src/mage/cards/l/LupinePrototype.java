package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LupinePrototype extends CardImpl {

    public LupinePrototype(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Lupine Prototype can't attack or block unless a player has no cards in hand.
        this.addAbility(new SimpleStaticAbility(
                new CantAttackBlockUnlessConditionSourceEffect(LupinePrototypeCondition.instance)
        ));
    }

    private LupinePrototype(final LupinePrototype card) {
        super(card);
    }

    @Override
    public LupinePrototype copy() {
        return new LupinePrototype(this);
    }
}

enum LupinePrototypeCondition implements Condition {
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
                .anyMatch(Set::isEmpty);
    }

    @Override
    public String toString() {
        return "a player has no cards in hand";
    }
}