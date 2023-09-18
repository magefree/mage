package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BirdAdmirer extends CardImpl {

    public BirdAdmirer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.w.WingShredder.class;

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private BirdAdmirer(final BirdAdmirer card) {
        super(card);
    }

    @Override
    public BirdAdmirer copy() {
        return new BirdAdmirer(this);
    }
}
