package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class EomerMarshalOfRohan extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
        = new FilterControlledCreaturePermanent("one or more other attacking legendary creatures you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(AttackingPredicate.instance);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public EomerMarshalOfRohan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever one or more other attacking legendary creatures you control die, untap all creatures you control. After this phase, there is an additional combat phase. This ability triggers only once each turn.
        Ability ability = new DiesCreatureTriggeredAbility(
            new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES), false, filter
        ).setTriggersOnceEachTurn(true);
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);
    }

    private EomerMarshalOfRohan(final EomerMarshalOfRohan card) {
        super(card);
    }

    @Override
    public EomerMarshalOfRohan copy() {
        return new EomerMarshalOfRohan(this);
    }
}
