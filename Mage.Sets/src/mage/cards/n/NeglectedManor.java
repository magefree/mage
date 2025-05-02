package mage.cards.n;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.APlayerHas13LifeCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeglectedManor extends CardImpl {

    public NeglectedManor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Neglected Manor enters tapped unless a player has 13 or less life.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(APlayerHas13LifeCondition.instance));

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private NeglectedManor(final NeglectedManor card) {
        super(card);
    }

    @Override
    public NeglectedManor copy() {
        return new NeglectedManor(this);
    }
}
