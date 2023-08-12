

package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class DestructiveForce extends CardImpl {

    private static final FilterControlledPermanent filterLand = new FilterControlledLandPermanent("lands");
    private static final FilterPermanent filterCreature = new FilterCreaturePermanent();


    public DestructiveForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}{R}");


        // Each player sacrifices five lands.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(5, filterLand));
        // Destructive Force deals 5 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(5, filterCreature));
    }

    private DestructiveForce(final DestructiveForce card) {
        super(card);
    }

    @Override
    public DestructiveForce copy() {
        return new DestructiveForce(this);
    }

}
