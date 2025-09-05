package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KravenProudPredator extends CardImpl {

    public KravenProudPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Top of the Food Chain -- Kraven's power is equal to the greatest mana value among permanents you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS)
        ).withFlavorWord("Top of the Food Chain"));
    }

    private KravenProudPredator(final KravenProudPredator card) {
        super(card);
    }

    @Override
    public KravenProudPredator copy() {
        return new KravenProudPredator(this);
    }
}
