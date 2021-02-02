
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 * @author BursegSardaukar
 */
public final class KyrenSniper extends CardImpl {

    public KyrenSniper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, you may have Kyren Sniper deal 1 damage to target player.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(1), TargetController.YOU, true);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private KyrenSniper(final KyrenSniper card) {
        super(card);
    }

    @Override
    public KyrenSniper copy() {
        return new KyrenSniper(this);
    }
}
