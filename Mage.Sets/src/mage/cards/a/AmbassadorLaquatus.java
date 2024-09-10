
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class AmbassadorLaquatus extends CardImpl {

    public AmbassadorLaquatus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {3}: Target player puts the top three cards of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsTargetEffect(3), new GenericManaCost(3));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AmbassadorLaquatus(final AmbassadorLaquatus card) {
        super(card);
    }

    @Override
    public AmbassadorLaquatus copy() {
        return new AmbassadorLaquatus(this);
    }
}
