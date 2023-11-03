package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreedyFreebooter extends CardImpl {

    public GreedyFreebooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Greedy Freebooter dies, scry 1 and create a Treasure token.
        Ability ability = new DiesSourceTriggeredAbility(new ScryEffect(1, false));
        ability.addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private GreedyFreebooter(final GreedyFreebooter card) {
        super(card);
    }

    @Override
    public GreedyFreebooter copy() {
        return new GreedyFreebooter(this);
    }
}
