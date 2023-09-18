package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CelestialEnforcer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("if you control a creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public CelestialEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}{W}, {T}: Tap target creature. Activate this ability only if you control a creature with flying.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{1}{W}"), condition
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CelestialEnforcer(final CelestialEnforcer card) {
        super(card);
    }

    @Override
    public CelestialEnforcer copy() {
        return new CelestialEnforcer(this);
    }
}
