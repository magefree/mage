package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.HumanWizardToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DocentOfPerfection extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.WIZARD), ComparisonType.MORE_THAN, 2
    );
    private static final Hint hint = new ValueHint(
            "Wizards you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.WIZARD))
    );

    public DocentOfPerfection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.f.FinalIteration.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, create a 1/1 blue Human Wizard creature token.
        // Then if you control three or more Wizards, transform Docent of Perfection.
        this.addAbility(new TransformAbility());
        Ability ability = new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new HumanWizardToken()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if you control three or more Wizards, transform {this}"
        ));
        this.addAbility(ability.addHint(hint));
    }

    private DocentOfPerfection(final DocentOfPerfection card) {
        super(card);
    }

    @Override
    public DocentOfPerfection copy() {
        return new DocentOfPerfection(this);
    }
}
