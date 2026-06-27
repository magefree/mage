package mage.cards.b;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BirninZanaPlaza extends CardImpl {

    public BirninZanaPlaza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

// This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When this land enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private BirninZanaPlaza(final BirninZanaPlaza card) {
        super(card);
    }

    @Override
    public BirninZanaPlaza copy() {
        return new BirninZanaPlaza(this);
    }
}
