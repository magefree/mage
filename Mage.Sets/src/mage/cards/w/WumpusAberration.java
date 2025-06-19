package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author DominionSpy
 */
public final class WumpusAberration extends CardImpl {

    private static final Condition condition = new InvertCondition(ManaWasSpentCondition.COLORLESS, "{C} wasn't spent to cast it");

    public WumpusAberration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast this spell, if {C} wasn't spent to cast it, target opponent may put a creature card from their hand onto the battlefield.
        Ability ability = new CastSourceTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(
                StaticFilters.FILTER_CARD_CREATURE, true
        ).setText("target opponent may put a creature card from their hand onto the battlefield")).withInterveningIf(condition);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private WumpusAberration(final WumpusAberration card) {
        super(card);
    }

    @Override
    public WumpusAberration copy() {
        return new WumpusAberration(this);
    }
}
