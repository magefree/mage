
package mage.cards.m;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.TwoOrMoreOpponentsCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MorphicPool extends CardImpl {

    public MorphicPool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Morphic Pool enters the battlefield tapped unless you have two or more opponents.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(TwoOrMoreOpponentsCondition.instance));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private MorphicPool(final MorphicPool card) {
        super(card);
    }

    @Override
    public MorphicPool copy() {
        return new MorphicPool(this);
    }
}
