package mage.cards.g;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
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
public final class GlitteringMassif extends CardImpl {

    public GlitteringMassif(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.PLAINS);

        // ({T}: Add {R} or {W}.)
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private GlitteringMassif(final GlitteringMassif card) {
        super(card);
    }

    @Override
    public GlitteringMassif copy() {
        return new GlitteringMassif(this);
    }
}
