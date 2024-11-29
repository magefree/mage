package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VileMutilator extends CardImpl {

    private static final FilterPermanent filter = new FilterEnchantmentPermanent("nontoken enchantment");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
        filter2.add(TokenPredicate.FALSE);
    }

    public VileMutilator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // As an additional cost to cast this spell, sacrifice a creature or enchantment.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Vile Mutilator enters, each opponent sacrifices a nontoken enchantment, then sacrifices a nontoken creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeOpponentsEffect(filter));
        ability.addEffect(new SacrificeOpponentsEffect(filter2).setText(", then sacrifices a nontoken creature of their choice"));
        this.addAbility(ability);
    }

    private VileMutilator(final VileMutilator card) {
        super(card);
    }

    @Override
    public VileMutilator copy() {
        return new VileMutilator(this);
    }
}
