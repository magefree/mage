package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZimoneQuandrixProdigy extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, ComparisonType.MORE_THAN, 7
    );

    public ZimoneQuandrixProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}, {T}: You may put a land card from your hand onto the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(
                new PutCardFromHandOntoBattlefieldEffect(
                        StaticFilters.FILTER_CARD_LAND_A, false, true
                ), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {4}, {T}: Draw a card. If you control eight or more lands, draw two cards instead.
        ability = new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(2),
                new DrawCardSourceControllerEffect(1),
                condition, "draw a card. If you control " +
                "eight or more lands, draw two cards instead"
        ), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(LandsYouControlHint.instance));
    }

    private ZimoneQuandrixProdigy(final ZimoneQuandrixProdigy card) {
        super(card);
    }

    @Override
    public ZimoneQuandrixProdigy copy() {
        return new ZimoneQuandrixProdigy(this);
    }
}
