package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class JeeringInstigator extends CardImpl {

    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public JeeringInstigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Morph {2}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{R}")));

        // When Jeering Instigator is turned face up, if it's your turn, gain control of another target creature until end of turn. Untap it. That creature gains haste until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new TurnedFaceUpSourceTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn), false, false),
                MyTurnCondition.instance,
                "When {this} is turned face up, if it's your turn, gain control of another target creature until end of turn. Untap that creature. It gains haste until end of turn.");
        ability.setWorksFaceDown(true);
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap that creature");
        ability.addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("It gains haste until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private JeeringInstigator(final JeeringInstigator card) {
        super(card);
    }

    @Override
    public JeeringInstigator copy() {
        return new JeeringInstigator(this);
    }
}
