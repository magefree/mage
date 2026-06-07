package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WiccanRisingMagician extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("another target nonland, nontoken permanent");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public WiccanRisingMagician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WARLOCK);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, exile another target nonland, nontoken permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new ExileReturnBattlefieldNextEndStepTargetEffect(),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private WiccanRisingMagician(final WiccanRisingMagician card) {
        super(card);
    }

    @Override
    public WiccanRisingMagician copy() {
        return new WiccanRisingMagician(this);
    }
}
