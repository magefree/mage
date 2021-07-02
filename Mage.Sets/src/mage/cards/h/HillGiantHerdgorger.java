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
public final class HillGiantHerdgorger extends CardImpl {

    public HillGiantHerdgorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // When Hill Giant Herdgorger enters the battlefield, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));
    }

    private HillGiantHerdgorger(final HillGiantHerdgorger card) {
        super(card);
    }

    @Override
    public HillGiantHerdgorger copy() {
        return new HillGiantHerdgorger(this);
    }
}
