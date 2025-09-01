package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class MilitantInquisitor extends CardImpl {

    public MilitantInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Miltant Inquisitor gets +1/+0 for each Equipment you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT), StaticValue.get(0), Duration.WhileOnBattlefield)));
    }

    private MilitantInquisitor(final MilitantInquisitor card) {
        super(card);
    }

    @Override
    public MilitantInquisitor copy() {
        return new MilitantInquisitor(this);
    }
}
