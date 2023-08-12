
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.RevealAndShuffleIntoLibrarySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class LegacyWeapon extends CardImpl {

    public LegacyWeapon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{7}");
        this.supertype.add(SuperType.LEGENDARY);

        // {W}{U}{B}{R}{G}: Exile target permanent.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ExileTargetEffect(),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
        // If Legacy Weapon would be put into a graveyard from anywhere, reveal Legacy Weapon and shuffle it into its owner's library instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new RevealAndShuffleIntoLibrarySourceEffect()));
    }

    private LegacyWeapon(final LegacyWeapon card) {
        super(card);
    }

    @Override
    public LegacyWeapon copy() {
        return new LegacyWeapon(this);
    }
}
