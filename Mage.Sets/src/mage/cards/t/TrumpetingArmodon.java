
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Sir-Speshkitty
 */
public final class TrumpetingArmodon extends CardImpl {

    public TrumpetingArmodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{G}: Target creature blocks Trumpeting Armodon this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(), new ManaCostsImpl<>("{1}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TrumpetingArmodon(final TrumpetingArmodon card) {
        super(card);
    }

    @Override
    public TrumpetingArmodon copy() {
        return new TrumpetingArmodon(this);
    }
}
