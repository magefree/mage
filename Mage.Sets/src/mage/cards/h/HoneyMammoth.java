package mage.cards.h;

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
public final class HoneyMammoth extends CardImpl {

    public HoneyMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Honey Mammoth enters the battlefield, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(4)));
    }

    private HoneyMammoth(final HoneyMammoth card) {
        super(card);
    }

    @Override
    public HoneyMammoth copy() {
        return new HoneyMammoth(this);
    }
}
