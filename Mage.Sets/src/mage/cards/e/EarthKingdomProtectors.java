package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthKingdomProtectors extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ALLY, "another target Ally you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public EarthKingdomProtectors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Sacrifice this creature: Another target Ally you control gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance()), new SacrificeSourceCost()
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private EarthKingdomProtectors(final EarthKingdomProtectors card) {
        super(card);
    }

    @Override
    public EarthKingdomProtectors copy() {
        return new EarthKingdomProtectors(this);
    }
}
