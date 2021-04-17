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
public final class SpringmaneCervin extends CardImpl {

    public SpringmaneCervin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Springmane Cervin enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));
    }

    private SpringmaneCervin(final SpringmaneCervin card) {
        super(card);
    }

    @Override
    public SpringmaneCervin copy() {
        return new SpringmaneCervin(this);
    }
}
