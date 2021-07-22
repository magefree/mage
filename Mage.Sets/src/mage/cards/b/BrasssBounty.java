
package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author LevelX2
 */
public final class BrasssBounty extends CardImpl {

    public BrasssBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{R}");

        // For each land you control, create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.getSpellAbility().addEffect(
                new CreateTokenEffect(new TreasureToken(), new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND))
                        .setText("For each land you control, create a Treasure token"));
    }

    private BrasssBounty(final BrasssBounty card) {
        super(card);
    }

    @Override
    public BrasssBounty copy() {
        return new BrasssBounty(this);
    }
}
