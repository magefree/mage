package mage.cards.m;

import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonologueTax extends CardImpl {

    public MonologueTax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever an opponent casts their second spell each turn, you create a Treasure token.
        this.addAbility(new CastSecondSpellTriggeredAbility(
                new CreateTokenEffect(new TreasureToken())
                        .setText("you create a Treasure token"),
                TargetController.OPPONENT
        ));
    }

    private MonologueTax(final MonologueTax card) {
        super(card);
    }

    @Override
    public MonologueTax copy() {
        return new MonologueTax(this);
    }
}
