package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.NewPlanetToken;
import mage.game.permanent.token.OfficerToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class SubspaceAnomaly extends CardImpl {

    public SubspaceAnomaly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}{W}, {T}, Sacrifice this land: Create two 1/1 red Officer creature tokens. Create a tapped Planet land token named New Planet with "{T}: Add one mana of any color."
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new OfficerToken(), 2), new ManaCostsImpl<>("{4}{W}"));

        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new CreateTokenEffect(new NewPlanetToken(), 1, true));
        this.addAbility(ability);
    }

    private SubspaceAnomaly(final SubspaceAnomaly card) {
        super(card);
    }

    @Override
    public SubspaceAnomaly copy() {
        return new SubspaceAnomaly(this);
    }
}
