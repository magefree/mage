package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class BardHeirOfGirion extends CardImpl {

    public BardHeirOfGirion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Other creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
            new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, true)
        ));

        // Whenever you attack, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            new DrawCardSourceControllerEffect(1), 1
        ));
    }

    private BardHeirOfGirion(final BardHeirOfGirion card) {
        super(card);
    }

    @Override
    public BardHeirOfGirion copy() {
        return new BardHeirOfGirion(this);
    }
}
