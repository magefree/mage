package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class KeralKeepDisciples extends CardImpl {

    public KeralKeepDisciples(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever you activate a loyalty ability of a Chandra planeswalker, Keral Keep Disciples deals 1 damage to each opponent.
        this.addAbility(new ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), SubType.CHANDRA));
    }

    private KeralKeepDisciples(final KeralKeepDisciples card) {
        super(card);
    }

    @Override
    public KeralKeepDisciples copy() {
        return new KeralKeepDisciples(this);
    }
}
