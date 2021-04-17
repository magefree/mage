package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.AssemblyWorkerToken;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class UrzasFactory extends CardImpl {

    public UrzasFactory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.subtype.add(SubType.URZAS);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {7}, {T}: Create a 2/2 colorless Assembly-Worker artifact creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new AssemblyWorkerToken()), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private UrzasFactory(final UrzasFactory card) {
        super(card);
    }

    @Override
    public UrzasFactory copy() {
        return new UrzasFactory(this);
    }
}
