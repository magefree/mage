package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BranchOfBoseiju extends CardImpl {

    public BranchOfBoseiju(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setGreen(true);
        this.nightCard = true;

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Branch of Boseiju gets +1/+1 for each land you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                LandsYouControlCount.instance, LandsYouControlCount.instance, Duration.WhileOnBattlefield
        ).setText("{this} gets +1/+1 for each land you control")));
    }

    private BranchOfBoseiju(final BranchOfBoseiju card) {
        super(card);
    }

    @Override
    public BranchOfBoseiju copy() {
        return new BranchOfBoseiju(this);
    }
}
