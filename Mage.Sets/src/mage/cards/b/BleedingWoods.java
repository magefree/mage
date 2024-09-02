package mage.cards.b;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.APlayerHas13LifeCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BleedingWoods extends CardImpl {

    public BleedingWoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Bleeding Woods enters tapped unless a player has 13 or less life.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(APlayerHas13LifeCondition.instance));

        // {T}: Add {R} or {B}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private BleedingWoods(final BleedingWoods card) {
        super(card);
    }

    @Override
    public BleedingWoods copy() {
        return new BleedingWoods(this);
    }
}
