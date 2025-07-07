package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlpharaelDreamingAcolyte extends CardImpl {

    public AlpharaelDreamingAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Alpharael enters, draw two cards. Then discard two cards unless you discard an artifact card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2));
        ability.addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2),
                new DiscardCardCost(StaticFilters.FILTER_CARD_ARTIFACT)
                        .setText("discard an artifact card instead of discarding two cards")
        ).setText("Then discard two cards unless you discard an artifact card"));
        this.addAbility(ability);

        // During your turn, Alpharael has deathtouch.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "during your turn, {this} has deathtouch"
        )));
    }

    private AlpharaelDreamingAcolyte(final AlpharaelDreamingAcolyte card) {
        super(card);
    }

    @Override
    public AlpharaelDreamingAcolyte copy() {
        return new AlpharaelDreamingAcolyte(this);
    }
}
