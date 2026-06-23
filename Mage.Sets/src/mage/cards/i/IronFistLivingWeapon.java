package mage.cards.i;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.target.common.TargetAnyTarget;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class IronFistLivingWeapon extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that targets a creature you control");

    static {
        filter.add(new TargetsPermanentPredicate(StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    public IronFistLivingWeapon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a spell that targets a creature you control, Iron Fist gains "{T}: Iron Fist deals damage equal to his power to any other target" until end of turn.
        Ability ability = new SimpleActivatedAbility(
            new DamageTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE)
                .setText("{this} deals damage equal to his power to any other target"),
            new TapSourceCost()
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new GainAbilitySourceEffect(ability, Duration.EndOfTurn), filter, false
        ));
    }

    private IronFistLivingWeapon(final IronFistLivingWeapon card) {
        super(card);
    }

    @Override
    public IronFistLivingWeapon copy() {
        return new IronFistLivingWeapon(this);
    }
}
