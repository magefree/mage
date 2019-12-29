package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawningAngel extends CardImpl {

    public DawningAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Dawning Angel enters the battlefield, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(4)));
    }

    private DawningAngel(final DawningAngel card) {
        super(card);
    }

    @Override
    public DawningAngel copy() {
        return new DawningAngel(this);
    }
}
