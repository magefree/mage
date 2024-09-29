package mage.cards.l;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.APlayerHas13LifeCondition;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LakesideShack extends CardImpl {

    public LakesideShack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Lakeside Shack enters tapped unless a player has 13 or less life.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(APlayerHas13LifeCondition.instance));

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private LakesideShack(final LakesideShack card) {
        super(card);
    }

    @Override
    public LakesideShack copy() {
        return new LakesideShack(this);
    }
}
