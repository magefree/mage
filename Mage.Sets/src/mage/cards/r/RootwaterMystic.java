
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
public final class RootwaterMystic extends CardImpl {

    public RootwaterMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{U}: Look at the top card of target player's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryTopCardTargetPlayerEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private RootwaterMystic(final RootwaterMystic card) {
        super(card);
    }

    @Override
    public RootwaterMystic copy() {
        return new RootwaterMystic(this);
    }
}
