
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author tiera3 - modified from GoblinInstigator
 */
public final class TinStreetCadet extends CardImpl {

    public TinStreetCadet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Tin Street Cadet becomes blocked, create a 1/1 red Goblin creature token.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new CreateTokenEffect(new GoblinToken()), false));
    }

    private TinStreetCadet(final TinStreetCadet card) {
        super(card);
    }

    @Override
    public TinStreetCadet copy() {
        return new TinStreetCadet(this);
    }
}
