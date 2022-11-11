package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Derpthemeus
 */
public final class DispersingOrb extends CardImpl {

    public DispersingOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");

        // {3}{U}, Sacrifice a permanent: Return target permanent to its owner's hand.
        Ability ability = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{3}{U}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent()));
        ability.addTarget(new TargetPermanent().withChooseHint("return to hand"));
        this.addAbility(ability);
    }

    private DispersingOrb(final DispersingOrb card) {
        super(card);
    }

    @Override
    public DispersingOrb copy() {
        return new DispersingOrb(this);
    }
}
