package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.NewPlanetToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class TemporalAnomaly extends CardImpl {

    public TemporalAnomaly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}{G}, {T}, Sacrifice this land: Mill four cards. You may put a permanent card from among them into your hand.
        // Create a tapped Planet land token named New Planet with "{T}: Add one mana of any color."
        Ability ability = new SimpleActivatedAbility(
            new MillThenPutInHandEffect(4, StaticFilters.FILTER_CARD_A_PERMANENT).withTextOptions("them"),
            new ManaCostsImpl<>("{3}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new CreateTokenEffect(new NewPlanetToken(), 1, true));
        this.addAbility(ability);
    }

    private TemporalAnomaly(final TemporalAnomaly card) {
        super(card);
    }

    @Override
    public TemporalAnomaly copy() {
        return new TemporalAnomaly(this);
    }
}
