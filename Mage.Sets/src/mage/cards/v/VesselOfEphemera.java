
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author fireshoes
 */
public final class VesselOfEphemera extends CardImpl {

    public VesselOfEphemera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // {2}{W}, Sacrifice Vessel of Ephemera: Create two 1/1 white Spirit creature tokens with flying.
        Effect effect = new CreateTokenEffect(new SpiritWhiteToken(), 2);
        effect.setText("Create two 1/1 white Spirit creature tokens with flying");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private VesselOfEphemera(final VesselOfEphemera card) {
        super(card);
    }

    @Override
    public VesselOfEphemera copy() {
        return new VesselOfEphemera(this);
    }
}
