package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.NewPlanetToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class GaseousAnomaly extends CardImpl {

    public GaseousAnomaly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {5}{U}, {T}, Sacrifice this land: Draw two cards.
        // Create a tapped Planet land token named New Planet with "{T}: Add one mana of any color."
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{5}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new CreateTokenEffect(new NewPlanetToken(), 1, true));
        this.addAbility(ability);
    }

    private GaseousAnomaly(final GaseousAnomaly card) {
        super(card);
    }

    @Override
    public GaseousAnomaly copy() {
        return new GaseousAnomaly(this);
    }
}
