
package mage.cards.p;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class PrismaticGeoscope extends CardImpl {

    public PrismaticGeoscope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Prismatic Geoscope enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // <i>Domain</i> &mdash; {T}: Add X mana in any combination of colors, where X is the number of basic land types among lands you control.
        Ability ability = new DynamicManaAbility(
                new Mana(0, 0, 0, 0, 0, 0, 1, 0), DomainValue.REGULAR, new TapSourceCost(),
                "Add X mana in any combination of colors,"
                + " where X is the number of basic land types among lands you control."
        );
        ability.setAbilityWord(AbilityWord.DOMAIN);
        this.addAbility(ability.addHint(DomainHint.instance));
    }

    private PrismaticGeoscope(final PrismaticGeoscope card) {
        super(card);
    }

    @Override
    public PrismaticGeoscope copy() {
        return new PrismaticGeoscope(this);
    }
}
