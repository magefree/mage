
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class LurkingInformant extends CardImpl {

    public LurkingInformant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U/B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // <i>({UB} can be paid with either {U} or {B}.)</i>
        // {2}, {tap}: Look at the top card of target player's library. You may put that card into that player's graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryTopCardTargetPlayerEffect(1, true), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private LurkingInformant(final LurkingInformant card) {
        super(card);
    }

    @Override
    public LurkingInformant copy() {
        return new LurkingInformant(this);
    }
}
