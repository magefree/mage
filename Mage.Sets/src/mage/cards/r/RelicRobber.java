package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RelicRobberToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelicRobber extends CardImpl {

    public RelicRobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Relic Robber deals combat damage to a player, that player creates a 0/1 colorless Goblin Construct artifact creature token with "This creature can't block" and "At the beginning of your upkeep, this creature deals 1 damage to you."
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenTargetEffect(new RelicRobberToken()), false, true
        ));
    }

    private RelicRobber(final RelicRobber card) {
        super(card);
    }

    @Override
    public RelicRobber copy() {
        return new RelicRobber(this);
    }
}
