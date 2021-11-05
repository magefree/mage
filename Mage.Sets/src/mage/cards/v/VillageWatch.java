package mage.cards.v;

import mage.MageInt;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VillageWatch extends CardImpl {

    public VillageWatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.v.VillageReavers.class;

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());
    }

    private VillageWatch(final VillageWatch card) {
        super(card);
    }

    @Override
    public VillageWatch copy() {
        return new VillageWatch(this);
    }
}
