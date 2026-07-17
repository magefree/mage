package mage.cards.f;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FootHeadquarters extends CardImpl {

    public FootHeadquarters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When this land enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private FootHeadquarters(final FootHeadquarters card) {
        super(card);
    }

    @Override
    public FootHeadquarters copy() {
        return new FootHeadquarters(this);
    }
}
