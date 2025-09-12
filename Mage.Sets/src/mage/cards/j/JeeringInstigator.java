package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class JeeringInstigator extends CardImpl {

    private static final Condition condition = new InvertCondition(NotMyTurnCondition.instance, "it's your turn");

    public JeeringInstigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Morph {2}{R}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{2}{R}")));

        // When Jeering Instigator is turned face up, if it's your turn, gain control of another target creature until end of turn. Untap it. That creature gains haste until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(
                new GainControlTargetEffect(Duration.EndOfTurn), false, false
        ).withInterveningIf(condition);
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        ability.setWorksFaceDown(true);
        this.addAbility(ability.addHint(MyTurnHint.instance));
    }

    private JeeringInstigator(final JeeringInstigator card) {
        super(card);
    }

    @Override
    public JeeringInstigator copy() {
        return new JeeringInstigator(this);
    }
}
