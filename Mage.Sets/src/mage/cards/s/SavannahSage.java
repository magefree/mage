package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavannahSage extends CardImpl {

    public SavannahSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Savannah Sage enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));
    }

    private SavannahSage(final SavannahSage card) {
        super(card);
    }

    @Override
    public SavannahSage copy() {
        return new SavannahSage(this);
    }
}
