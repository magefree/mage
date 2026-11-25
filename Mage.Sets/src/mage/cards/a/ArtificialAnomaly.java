package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.NewPlanetToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class ArtificialAnomaly extends CardImpl {

    public ArtificialAnomaly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}{B}, {T}, Sacrifice this land: Each opponent loses 2 life and you gain 2 life.
        // Create a tapped Planet land token named New Planet with “{T}: Add one mana of any color.”
        Ability ability = new SimpleActivatedAbility(
            new LoseLifeOpponentsEffect(2),
            new ManaCostsImpl<>("{3}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addEffect(new CreateTokenEffect(new NewPlanetToken(), 1, true));
        this.addAbility(ability);
    }

    private ArtificialAnomaly(final ArtificialAnomaly card) {
        super(card);
    }

    @Override
    public ArtificialAnomaly copy() {
        return new ArtificialAnomaly(this);
    }
}
