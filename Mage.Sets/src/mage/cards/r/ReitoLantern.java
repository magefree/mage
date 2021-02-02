
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class ReitoLantern extends CardImpl {

    public ReitoLantern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Put target card from a graveyard on the bottom of its owner's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(false), new GenericManaCost(3));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

    }

    private ReitoLantern(final ReitoLantern card) {
        super(card);
    }

    @Override
    public ReitoLantern copy() {
        return new ReitoLantern(this);
    }
}
