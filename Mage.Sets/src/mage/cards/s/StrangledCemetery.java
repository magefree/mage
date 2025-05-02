package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.APlayerHas13LifeCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StrangledCemetery extends CardImpl {

    public StrangledCemetery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Strangled Cemetery enters tapped unless a player has 13 or less life.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(APlayerHas13LifeCondition.instance));

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private StrangledCemetery(final StrangledCemetery card) {
        super(card);
    }

    @Override
    public StrangledCemetery copy() {
        return new StrangledCemetery(this);
    }
}
