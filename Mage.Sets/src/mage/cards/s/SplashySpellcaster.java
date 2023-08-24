package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class SplashySpellcaster extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("other target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SplashySpellcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast an instant or sorcery spell, create a Sorcerer Role token attached to up to one other target creature you control.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new CreateRoleAttachedTargetEffect(RoleType.SORCERER),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1, filter, false));

        this.addAbility(ability);
    }

    private SplashySpellcaster(final SplashySpellcaster card) {
        super(card);
    }

    @Override
    public SplashySpellcaster copy() {
        return new SplashySpellcaster(this);
    }
}
