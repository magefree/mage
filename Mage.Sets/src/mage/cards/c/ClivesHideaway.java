package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClivesHideaway extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY, ComparisonType.MORE_THAN, 3
    );
    private static final Hint hint = new ValueHint(
            "Legendary creatures you control",
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY)
    );

    public ClivesHideaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.TOWN);

        // Hideaway 4
        this.addAbility(new HideawayAbility(this, 4));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: You may play the exiled card without paying its mana cost if you control four or more legendary creatures.
        Ability ability = new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new HideawayPlayEffect(), condition, "you may play the exiled card " +
                "without paying its mana cost if you control four or more legendary creatures"
        ), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(hint));
    }

    private ClivesHideaway(final ClivesHideaway card) {
        super(card);
    }

    @Override
    public ClivesHideaway copy() {
        return new ClivesHideaway(this);
    }
}
