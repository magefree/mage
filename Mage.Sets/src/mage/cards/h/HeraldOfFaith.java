package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class HeraldOfFaith extends CardImpl {

    public HeraldOfFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Herald of Faith attacks, you gain 2 life.
        this.addAbility(new AttacksTriggeredAbility(new GainLifeEffect(2), false));
    }

    private HeraldOfFaith(final HeraldOfFaith card) {
        super(card);
    }

    @Override
    public HeraldOfFaith copy() {
        return new HeraldOfFaith(this);
    }
}
