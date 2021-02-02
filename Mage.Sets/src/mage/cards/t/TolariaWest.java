
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.TransmuteAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jonubuu
 */
public final class TolariaWest extends CardImpl {

    public TolariaWest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Tolaria West enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
        // Transmute {1}{U}{U}
        this.addAbility(new TransmuteAbility("{1}{U}{U}"));
    }

    private TolariaWest(final TolariaWest card) {
        super(card);
    }

    @Override
    public TolariaWest copy() {
        return new TolariaWest(this);
    }
}
