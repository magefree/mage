package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ApocalypseRunner extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature you control with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ApocalypseRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // {T}: Target creature you control with power 2 or less gains lifelink until end of turn and can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(LifelinkAbility.getInstance()), new TapSourceCost());
        ability.addEffect(new CantBeBlockedTargetEffect().setText("and can't be blocked this turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private ApocalypseRunner(final ApocalypseRunner card) {
        super(card);
    }

    @Override
    public ApocalypseRunner copy() {
        return new ApocalypseRunner(this);
    }
}
