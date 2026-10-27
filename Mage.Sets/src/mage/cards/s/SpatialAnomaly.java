package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.NewPlanetToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class SpatialAnomaly extends CardImpl {

    public SpatialAnomaly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}{R}, {T}, Sacrifice this land: It deals 2 damage to target creature.
        // Create a tapped Planet land token named New Planet with “{T}: Add one mana of any color.”
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new ManaCostsImpl<>("{4}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new CreateTokenEffect(new NewPlanetToken(), 1, true));
        this.addAbility(ability);
    }

    private SpatialAnomaly(final SpatialAnomaly card) {
        super(card);
    }

    @Override
    public SpatialAnomaly copy() {
        return new SpatialAnomaly(this);
    }
}
