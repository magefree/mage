package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProsperousBandit extends CardImpl {

    public ProsperousBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Offspring {1}
        this.addAbility(new OffspringAbility("{1}"));

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever this creature deals combat damage to a player, create that many tapped Treasure tokens.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(
                new TreasureToken(), SavedDamageValue.MANY, true, false
        ), false, true));
    }

    private ProsperousBandit(final ProsperousBandit card) {
        super(card);
    }

    @Override
    public ProsperousBandit copy() {
        return new ProsperousBandit(this);
    }
}
