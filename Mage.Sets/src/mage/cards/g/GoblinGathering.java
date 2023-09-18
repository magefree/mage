package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinGathering extends CardImpl {

    public GoblinGathering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Create a number of 1/1 red Goblin creature tokens equal to two plus the number of cards named Goblin Gathering in your graveyard.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new GoblinToken(), GoblinGatheringDynamicValue.instance
        ));
        this.getSpellAbility().addHint(new ValueHint("You can create tokens", GoblinGatheringDynamicValue.instance));
    }

    private GoblinGathering(final GoblinGathering card) {
        super(card);
    }

    @Override
    public GoblinGathering copy() {
        return new GoblinGathering(this);
    }
}

enum GoblinGatheringDynamicValue implements DynamicValue {

    instance;
    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new NamePredicate("Goblin Gathering"));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            amount += player.getGraveyard().count(
                    filter,
                    sourceAbility.getControllerId(), sourceAbility, game
            );
        }
        return amount + 2;
    }

    @Override
    public GoblinGatheringDynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "two plus the number of cards named Goblin Gathering in your graveyard";
    }
}
