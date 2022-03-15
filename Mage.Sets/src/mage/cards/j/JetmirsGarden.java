package mage.cards.j;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JetmirsGarden extends CardImpl {

    public JetmirsGarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.PLAINS);

        // ({T}: Add {R}, {G}, or {W}.)
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());

        // Jetmir's Garden enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
    }

    private JetmirsGarden(final JetmirsGarden card) {
        super(card);
    }

    @Override
    public JetmirsGarden copy() {
        return new JetmirsGarden(this);
    }
}
