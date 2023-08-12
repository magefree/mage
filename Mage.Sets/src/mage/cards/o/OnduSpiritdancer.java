package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OnduSpiritdancer extends CardImpl {

    public OnduSpiritdancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an enchantment enters the battlefield under your control, you may create a token that's a copy of it. Do this only once each turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenCopyTargetEffect().setText("create a token that's a copy of it"),
                StaticFilters.FILTER_PERMANENT_ENCHANTMENT, true, SetTargetPointer.PERMANENT, null
        ).setDoOnlyOnceEachTurn(true));
    }

    private OnduSpiritdancer(final OnduSpiritdancer card) {
        super(card);
    }

    @Override
    public OnduSpiritdancer copy() {
        return new OnduSpiritdancer(this);
    }
}
