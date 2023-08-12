package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BilboRetiredBurglar extends CardImpl {

    public BilboRetiredBurglar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Bilbo, Retired Burglar enters or leaves the battlefield, the Ring tempts you.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(new TheRingTemptsYouEffect(), false));

        // Whenever Bilbo deals combat damage to a player, create a Treasure token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new TreasureToken()), false));
    }

    private BilboRetiredBurglar(final BilboRetiredBurglar card) {
        super(card);
    }

    @Override
    public BilboRetiredBurglar copy() {
        return new BilboRetiredBurglar(this);
    }
}
