package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HomicideInvestigator extends CardImpl {

    public HomicideInvestigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more nontoken creatures you control die, investigate. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new InvestigateEffect(), false, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
        ).setTriggerPhrase("Whenever one or more nontoken creatures you control die, ").setTriggersOnceEachTurn(true));
    }

    private HomicideInvestigator(final HomicideInvestigator card) {
        super(card);
    }

    @Override
    public HomicideInvestigator copy() {
        return new HomicideInvestigator(this);
    }
}
