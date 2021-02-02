
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author anonymous
 */
public final class OrcishSpy extends CardImpl {

    public OrcishSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Look at the top three cards of target player's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryTopCardTargetPlayerEffect(3), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private OrcishSpy(final OrcishSpy card) {
        super(card);
    }

    @Override
    public OrcishSpy copy() {
        return new OrcishSpy(this);
    }
}
