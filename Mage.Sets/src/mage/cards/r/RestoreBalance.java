package mage.cards.r;

import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.BalanceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class RestoreBalance extends CardImpl {

    public RestoreBalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setWhite(true);

        // Suspend 6-{W}
        this.addAbility(new SuspendAbility(6, new ColoredManaCost(ColoredManaSymbol.W), this));
        // Each player chooses a number of lands they control equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players sacrifice creatures and discard cards the same way.
        this.getSpellAbility().addEffect(new BalanceEffect());
    }

    private RestoreBalance(final RestoreBalance card) {
        super(card);
    }

    @Override
    public RestoreBalance copy() {
        return new RestoreBalance(this);
    }
}
