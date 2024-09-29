package mage.cards.v;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.TwoOrMoreOpponentsCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VaultOfChampions extends CardImpl {

    public VaultOfChampions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Vault of Champions enters the battlefield tapped unless you have two or more opponents.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(TwoOrMoreOpponentsCondition.instance));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private VaultOfChampions(final VaultOfChampions card) {
        super(card);
    }

    @Override
    public VaultOfChampions copy() {
        return new VaultOfChampions(this);
    }
}
