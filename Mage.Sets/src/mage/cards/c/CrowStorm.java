
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.StormCrowToken;

/**
 *
 * @author spjspj
 */
public final class CrowStorm extends CardImpl {

    public CrowStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Create a 1/2 blue Bird creature token with flying named Storm Crow.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new StormCrowToken(), 1));

        // Storm (When you cast this spell, copy it for each spell cast before it this turn.)
        this.addAbility(new StormAbility());
    }

    private CrowStorm(final CrowStorm card) {
        super(card);
    }

    @Override
    public CrowStorm copy() {
        return new CrowStorm(this);
    }
}
