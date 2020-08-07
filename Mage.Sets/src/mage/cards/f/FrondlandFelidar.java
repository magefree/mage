package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrondlandFelidar extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures you control with vigilance");

    static {
        filter.add(new AbilityPredicate(VigilanceAbility.class));
    }

    public FrondlandFelidar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Creatures you control with vigilance have "{1}, {T}: Tap target creature."
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ContinuousEffect effect = new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield, filter);
        effect.setDependedToType(DependencyType.AddingAbility);
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private FrondlandFelidar(final FrondlandFelidar card) {
        super(card);
    }

    @Override
    public FrondlandFelidar copy() {
        return new FrondlandFelidar(this);
    }
}
