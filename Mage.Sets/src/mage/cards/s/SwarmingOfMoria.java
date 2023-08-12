package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwarmingOfMoria extends CardImpl {

    public SwarmingOfMoria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Create a Treasure token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));

        // Amass Orcs 2.
        this.getSpellAbility().addEffect(new AmassEffect(2, SubType.ORC).concatBy("<br>"));
    }

    private SwarmingOfMoria(final SwarmingOfMoria card) {
        super(card);
    }

    @Override
    public SwarmingOfMoria copy() {
        return new SwarmingOfMoria(this);
    }
}
