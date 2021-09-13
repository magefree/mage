package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarvesttideInfiltrator extends CardImpl {

    public HarvesttideInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.h.HarvesttideAssailant.class;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());
    }

    private HarvesttideInfiltrator(final HarvesttideInfiltrator card) {
        super(card);
    }

    @Override
    public HarvesttideInfiltrator copy() {
        return new HarvesttideInfiltrator(this);
    }
}
