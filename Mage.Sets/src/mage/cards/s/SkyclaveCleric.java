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
public final class SkyclaveCleric extends CardImpl {

    public SkyclaveCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.s.SkyclaveBasilica.class;

        // When Skyclave Cleric enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));
    }

    private SkyclaveCleric(final SkyclaveCleric card) {
        super(card);
    }

    @Override
    public SkyclaveCleric copy() {
        return new SkyclaveCleric(this);
    }
}
