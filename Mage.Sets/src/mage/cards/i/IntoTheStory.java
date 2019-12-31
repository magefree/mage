package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.Graveyard;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntoTheStory extends CardImpl {

    public IntoTheStory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}{U}");

        // This spell costs {3} less to cast if an opponent has seven or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.STACK, new SpellCostReductionSourceEffect(3, IntoTheStoryCondition.instance)
        ).setRuleAtTheTop(true));

        // Draw four cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
    }

    private IntoTheStory(final IntoTheStory card) {
        super(card);
    }

    @Override
    public IntoTheStory copy() {
        return new IntoTheStory(this);
    }
}

enum IntoTheStoryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .map(Player::getGraveyard)
                .mapToInt(Graveyard::size)
                .anyMatch(i -> i >= 7);
    }

    @Override
    public String toString() {
        return "an opponent has seven or more cards in their graveyard";
    }
}
