
package mage.cards.b;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.TwoOrMoreOpponentsCondition;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BountifulPromenade extends CardImpl {

    public BountifulPromenade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Bountiful Promenade enters the battlefield tapped unless you have two or more opponents.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(TwoOrMoreOpponentsCondition.instance));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private BountifulPromenade(final BountifulPromenade card) {
        super(card);
    }

    @Override
    public BountifulPromenade copy() {
        return new BountifulPromenade(this);
    }
}
