package mage.cards.e;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.APlayerHas13LifeCondition;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtchedCornfield extends CardImpl {

    public EtchedCornfield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Etched Cornfield enters tapped unless a player has 13 or less life.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(APlayerHas13LifeCondition.instance));

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private EtchedCornfield(final EtchedCornfield card) {
        super(card);
    }

    @Override
    public EtchedCornfield copy() {
        return new EtchedCornfield(this);
    }
}
