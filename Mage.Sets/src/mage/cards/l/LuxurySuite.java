
package mage.cards.l;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.TwoOrMoreOpponentsCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LuxurySuite extends CardImpl {

    public LuxurySuite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Luxury Suite enters the battlefield tapped unless you have two or more opponents.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(TwoOrMoreOpponentsCondition.instance));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private LuxurySuite(final LuxurySuite card) {
        super(card);
    }

    @Override
    public LuxurySuite copy() {
        return new LuxurySuite(this);
    }
}
