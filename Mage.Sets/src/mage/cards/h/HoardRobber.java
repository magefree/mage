package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HoardRobber extends CardImpl {

    public HoardRobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Hoard Robber deals combat damage to a player, create a Treasure token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), false
        ));
    }

    private HoardRobber(final HoardRobber card) {
        super(card);
    }

    @Override
    public HoardRobber copy() {
        return new HoardRobber(this);
    }
}
