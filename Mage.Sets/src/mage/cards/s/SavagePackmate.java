package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavagePackmate extends CardImpl {

    public SavagePackmate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, true
        )));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private SavagePackmate(final SavagePackmate card) {
        super(card);
    }

    @Override
    public SavagePackmate copy() {
        return new SavagePackmate(this);
    }
}
