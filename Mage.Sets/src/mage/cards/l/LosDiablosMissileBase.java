package mage.cards.l;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author muz
 */
public final class LosDiablosMissileBase extends CardImpl {

    public LosDiablosMissileBase(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When this land enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private LosDiablosMissileBase(final LosDiablosMissileBase card) {
        super(card);
    }

    @Override
    public LosDiablosMissileBase copy() {
        return new LosDiablosMissileBase(this);
    }
}
