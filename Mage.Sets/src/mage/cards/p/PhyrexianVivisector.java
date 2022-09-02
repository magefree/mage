package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhyrexianVivisector extends CardImpl {

    public PhyrexianVivisector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature you control dies, scry 1.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new ScryEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));
    }

    private PhyrexianVivisector(final PhyrexianVivisector card) {
        super(card);
    }

    @Override
    public PhyrexianVivisector copy() {
        return new PhyrexianVivisector(this);
    }
}
