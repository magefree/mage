package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
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
public final class SacredPeaks extends CardImpl {

    public SacredPeaks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.PLAINS);

        // ({T}: Add {R} or {W}.)
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // Sacred Peaks enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private SacredPeaks(final SacredPeaks card) {
        super(card);
    }

    @Override
    public SacredPeaks copy() {
        return new SacredPeaks(this);
    }
}
