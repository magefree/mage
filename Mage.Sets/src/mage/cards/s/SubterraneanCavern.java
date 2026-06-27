package mage.cards.s;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SubterraneanCavern extends CardImpl {

    public SubterraneanCavern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When this land enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private SubterraneanCavern(final SubterraneanCavern card) {
        super(card);
    }

    @Override
    public SubterraneanCavern copy() {
        return new SubterraneanCavern(this);
    }
}
