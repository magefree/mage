package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.BoastAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofFromPlaneswalkersAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class EradicatorValkyrie extends CardImpl {

    public EradicatorValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Hexproof from planeswalkers
        this.addAbility(HexproofFromPlaneswalkersAbility.getInstance());

        // Boast — {1}{B}, Sacrifice a creature: Each opponent sacrifices a creature or planeswalker.
        Ability ability = new BoastAbility(new SacrificeOpponentsEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER
        ), "{1}{B}");
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    private EradicatorValkyrie(final EradicatorValkyrie card) {
        super(card);
    }

    @Override
    public EradicatorValkyrie copy() {
        return new EradicatorValkyrie(this);
    }
}
