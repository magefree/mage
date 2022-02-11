package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PortraitOfMichiko extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            StaticFilters.FILTER_PERMANENT_CONTROLLED_ARTIFACT_OR_ENCHANTMENT
    );

    public PortraitOfMichiko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setWhite(true);
        this.nightCard = true;

        // Portrait of Michiko gets +1/+1 for each artifact and/or enchantment you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)
                .setText("{this} gets +1/+1 for each artifact and/or enchantment you control")));
    }

    private PortraitOfMichiko(final PortraitOfMichiko card) {
        super(card);
    }

    @Override
    public PortraitOfMichiko copy() {
        return new PortraitOfMichiko(this);
    }
}
