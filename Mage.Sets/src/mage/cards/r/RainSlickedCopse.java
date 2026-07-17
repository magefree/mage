package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RainSlickedCopse extends CardImpl {

    public RainSlickedCopse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {G} or {U}.)
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RainSlickedCopse(final RainSlickedCopse card) {
        super(card);
    }

    @Override
    public RainSlickedCopse copy() {
        return new RainSlickedCopse(this);
    }
}
