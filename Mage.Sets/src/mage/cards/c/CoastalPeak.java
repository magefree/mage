package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoastalPeak extends CardImpl {

    public CoastalPeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {U} or {R}.)
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private CoastalPeak(final CoastalPeak card) {
        super(card);
    }

    @Override
    public CoastalPeak copy() {
        return new CoastalPeak(this);
    }
}
