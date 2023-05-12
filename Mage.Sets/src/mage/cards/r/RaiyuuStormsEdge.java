package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.condition.common.FirstCombatPhaseCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class RaiyuuStormsEdge extends CardImpl {

    public RaiyuuStormsEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever a Samurai or Warrior you control attacks alone, untap it. If it's the first combat phase of the turn, there is an additional combat phase after this phase.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
                new UntapTargetEffect().setText("untap it"),
                StaticFilters.FILTER_CONTROLLED_SAMURAI_OR_WARRIOR,
                true, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new AdditionalCombatPhaseEffect(), FirstCombatPhaseCondition.instance,
                "If it's the first combat phase of the turn, there is an additional combat phase after this phase"
        ));
        this.addAbility(ability);
    }

    private RaiyuuStormsEdge(final RaiyuuStormsEdge card) {
        super(card);
    }

    @Override
    public RaiyuuStormsEdge copy() {
        return new RaiyuuStormsEdge(this);
    }
}
