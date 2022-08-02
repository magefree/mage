package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EiganjoExemplar extends CardImpl {

    public EiganjoExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a Samurai or Warrior you control attacks alone, it gets +1/+1 until end of turn.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(
                new BoostTargetEffect(1, 1),
                StaticFilters.FILTER_CONTROLLED_SAMURAI_OR_WARRIOR, true, false
        ));
    }

    private EiganjoExemplar(final EiganjoExemplar card) {
        super(card);
    }

    @Override
    public EiganjoExemplar copy() {
        return new EiganjoExemplar(this);
    }
}
