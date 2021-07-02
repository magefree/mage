package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwarmingGoblins extends CardImpl {

    public SwarmingGoblins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Swarming Goblins enters the battlefield, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));

        // 1-9 | Create a 1/1 red Goblin creature token.
        effect.addTableEntry(
                1, 9,
                new CreateTokenEffect(new GoblinToken())
        );

        // 10-19 | Create two of those tokens.
        effect.addTableEntry(
                10, 19,
                new CreateTokenEffect(new GoblinToken(), 2)
                        .setText("create two of those tokens")
        );

        // 20 | Create three of those tokens.
        effect.addTableEntry(
                20, 20,
                new CreateTokenEffect(new GoblinToken(), 3)
                        .setText("create three of those tokens")
        );
    }

    private SwarmingGoblins(final SwarmingGoblins card) {
        super(card);
    }

    @Override
    public SwarmingGoblins copy() {
        return new SwarmingGoblins(this);
    }
}
