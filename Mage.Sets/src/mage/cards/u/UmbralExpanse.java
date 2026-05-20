package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UmbralExpanse extends CardImpl {

    public UmbralExpanse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {W} or {B}.)
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private UmbralExpanse(final UmbralExpanse card) {
        super(card);
    }

    @Override
    public UmbralExpanse copy() {
        return new UmbralExpanse(this);
    }
}
