
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class SaltwaterStalwart extends CardImpl {

    public SaltwaterStalwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Saltwater Stalwart deals damage to an opponent, target player draws a card.
        Ability ability = new DealsDamageToOpponentTriggeredAbility(new DrawCardTargetEffect(1));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SaltwaterStalwart(final SaltwaterStalwart card) {
        super(card);
    }

    @Override
    public SaltwaterStalwart copy() {
        return new SaltwaterStalwart(this);
    }
}
