package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EllieVengefulHunter extends CardImpl {

    public EllieVengefulHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Pay 2 life, Sacrifice another creature: Ellie deals 2 damage to target player and gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new PayLifeCost(2));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE));
        ability.addEffect(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains indestructible until end of turn"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Partner--Survivors
        this.addAbility(PartnerVariantType.SURVIVORS.makeAbility());
    }

    private EllieVengefulHunter(final EllieVengefulHunter card) {
        super(card);
    }

    @Override
    public EllieVengefulHunter copy() {
        return new EllieVengefulHunter(this);
    }
}
