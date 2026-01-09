package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE;

/**
 * @author JRHerlehy
 */
public final class AcademyJourneymage extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterControlledPermanent(SubType.WIZARD, "you control a Wizard")
    );
    private static final Hint hint = new ConditionHint(condition, "You control a Wizard");

    public AcademyJourneymage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // This spell costs {1} less to cast if you control a Wizard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(1, condition));
        ability.setRuleAtTheTop(true);
        ability.addHint(hint);
        this.addAbility(ability);

        // When Academy Journeymage enters the battlefield, return target creature an opponent controls to its owner's hand.
        ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent(FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private AcademyJourneymage(final AcademyJourneymage card) {
        super(card);
    }

    @Override
    public AcademyJourneymage copy() {
        return new AcademyJourneymage(this);
    }
}
