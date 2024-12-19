
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class RumblingSlum extends CardImpl {

    public RumblingSlum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, Rumbling Slum deals 1 damage to each player.
        Effect effect = new DamagePlayersEffect(1, TargetController.ANY);
        effect.setText("{this} deals 1 damage to each player");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(effect));
    }

    private RumblingSlum(final RumblingSlum card) {
        super(card);
    }

    @Override
    public RumblingSlum copy() {
        return new RumblingSlum(this);
    }
}
