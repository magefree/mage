package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.HumanWizardToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DocentOfPerfection extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WIZARD, "Wizards");
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.WIZARD);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            filter2, ComparisonType.MORE_THAN, 2, true
    );
    private static final Hint hint = new ValueHint(
            "Wizards you control", new PermanentsOnBattlefieldCount(filter2)
    );

    public DocentOfPerfection(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.INSECT, SubType.HORROR}, "{3}{U}{U}",
                "Final Iteration",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.INSECT}, ""
        );
        this.getLeftHalfCard().setPT(5, 4);
        this.getRightHalfCard().setPT(6, 5);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, create a 1/1 blue Human Wizard creature token. Then if you control three or more Wizards, transform Docent of Perfection.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new HumanWizardToken()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if you control three or more Wizards, transform {this}"
        ));
        this.getLeftHalfCard().addAbility(ability.addHint(hint));

        // Final Iteration
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Wizards you control get +2/+1 and have flying.
        ability = new SimpleStaticAbility(new BoostControlledEffect(
                2, 1, Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        ).setText("and have flying"));
        this.getRightHalfCard().addAbility(ability);

        // Whenever you cast an instant or sorcery spell, create a 1/1 blue Human Wizard creature token.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new HumanWizardToken()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private DocentOfPerfection(final DocentOfPerfection card) {
        super(card);
    }

    @Override
    public DocentOfPerfection copy() {
        return new DocentOfPerfection(this);
    }
}
