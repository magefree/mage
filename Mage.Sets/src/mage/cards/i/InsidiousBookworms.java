
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author LoneFox
 */
public final class InsidiousBookworms extends CardImpl {

    public InsidiousBookworms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.WORM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Insidious Bookworms dies, you may pay {1}{B}. If you do, target player discards a card at random.
        Ability ability = new DiesSourceTriggeredAbility(new DoIfCostPaid(new DiscardTargetEffect(1, true), new ManaCostsImpl<>("{1}{B}")));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private InsidiousBookworms(final InsidiousBookworms card) {
        super(card);
    }

    @Override
    public InsidiousBookworms copy() {
        return new InsidiousBookworms(this);
    }
}
