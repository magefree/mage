package mage.cards.w;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WoodedRidgeline extends CardImpl {

    public WoodedRidgeline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.FOREST);

        // ({T}: Add {R} or {G}.)
        this.addAbility(new GreenManaAbility());
        this.addAbility(new RedManaAbility());

        // Wooded Ridgeline enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private WoodedRidgeline(final WoodedRidgeline card) {
        super(card);
    }

    @Override
    public WoodedRidgeline copy() {
        return new WoodedRidgeline(this);
    }
}
