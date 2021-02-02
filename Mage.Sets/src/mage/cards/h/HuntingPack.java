
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BeastToken2;

/**
 *
 * @author Plopman
 */
public final class HuntingPack extends CardImpl {

    public HuntingPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{G}{G}");

        // Create a 4/4 green Beast creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken2(), 1));
        // Storm
        this.addAbility(new StormAbility());
    }

    private HuntingPack(final HuntingPack card) {
        super(card);
    }

    @Override
    public HuntingPack copy() {
        return new HuntingPack(this);
    }
}
