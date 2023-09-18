package mage.cards.h;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HauntedMire extends CardImpl {

    public HauntedMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.FOREST);

        // ({T}: Add {B} or {G}.)
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // Haunted Mire enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private HauntedMire(final HauntedMire card) {
        super(card);
    }

    @Override
    public HauntedMire copy() {
        return new HauntedMire(this);
    }
}
