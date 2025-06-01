package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrahaTia extends CardImpl {

    public GrahaTia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // The Allagan Eye - Whenever one or more other creatures and/or artifacts you control die, draw a card. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT
        ).setTriggerPhrase("Whenever one or more other creatures and/or artifacts you control die, ")
                .setTriggersLimitEachTurn(1).withFlavorWord("The Allagan Eye"));
    }

    private GrahaTia(final GrahaTia card) {
        super(card);
    }

    @Override
    public GrahaTia copy() {
        return new GrahaTia(this);
    }
}
