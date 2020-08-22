package mage.cards.v;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.OneOpponentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
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
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(
                        new TapSourceEffect(),
                        OneOpponentCondition.instance,
                        "tapped unless you have two or more opponents"
                ), "tapped unless you have two or more opponents"
        ));

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
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
