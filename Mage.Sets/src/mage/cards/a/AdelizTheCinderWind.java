package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public final class AdelizTheCinderWind extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wizards");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.WIZARD));
    }

    public AdelizTheCinderWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, Wizards you control get +1/+1 until end of turn.
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter);
        Ability ability = new SpellCastControllerTriggeredAbility(effect, StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false);
        this.addAbility(ability);
    }

    public AdelizTheCinderWind(final AdelizTheCinderWind card) {
        super(card);
    }

    @Override
    public AdelizTheCinderWind copy() {
        return new AdelizTheCinderWind(this);
    }
}
