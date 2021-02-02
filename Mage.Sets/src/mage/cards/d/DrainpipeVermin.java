
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class DrainpipeVermin extends CardImpl {

    public DrainpipeVermin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Drainpipe Vermin dies, you may pay {B}. If you do, target player discards a card.
        Ability ability = new DiesSourceTriggeredAbility(new DoIfCostPaid(new DiscardTargetEffect(1), new ColoredManaCost(ColoredManaSymbol.B)), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DrainpipeVermin(final DrainpipeVermin card) {
        super(card);
    }

    @Override
    public DrainpipeVermin copy() {
        return new DrainpipeVermin(this);
    }
}