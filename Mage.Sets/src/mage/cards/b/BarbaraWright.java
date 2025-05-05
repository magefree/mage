package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.keyword.ReadAheadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarbaraWright extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SAGA, "Sagas");

    public BarbaraWright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // History Teacher -- Sagas you control have read ahead.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                ReadAheadAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("Sagas you control have read ahead")).withFlavorWord("History Teacher"));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private BarbaraWright(final BarbaraWright card) {
        super(card);
    }

    @Override
    public BarbaraWright copy() {
        return new BarbaraWright(this);
    }
}
