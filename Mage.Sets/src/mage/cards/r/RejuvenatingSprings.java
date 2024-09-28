package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.TwoOrMoreOpponentsCondition;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RejuvenatingSprings extends CardImpl {

    public RejuvenatingSprings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Rejuvenating Springs enters the battlefield tapped unless you have two or more opponents.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(TwoOrMoreOpponentsCondition.instance));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private RejuvenatingSprings(final RejuvenatingSprings card) {
        super(card);
    }

    @Override
    public RejuvenatingSprings copy() {
        return new RejuvenatingSprings(this);
    }
}
