
package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.TwoOrMoreOpponentsCondition;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpireGarden extends CardImpl {

    public SpireGarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Spire Garden enters the battlefield tapped unless you have two or more opponents.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(TwoOrMoreOpponentsCondition.instance));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private SpireGarden(final SpireGarden card) {
        super(card);
    }

    @Override
    public SpireGarden copy() {
        return new SpireGarden(this);
    }
}
