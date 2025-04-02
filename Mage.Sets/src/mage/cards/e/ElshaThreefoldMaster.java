package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.MonasteryMentorToken;

/**
 *
 * @author Grath
 */
public final class ElshaThreefoldMaster extends CardImpl {

    public ElshaThreefoldMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever Elsha deals combat damage to a player, create that many 1/1 white Monk creature tokens with prowess.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new MonasteryMentorToken(), SavedDamageValue.MANY), false, true));
    }

    private ElshaThreefoldMaster(final ElshaThreefoldMaster card) {
        super(card);
    }

    @Override
    public ElshaThreefoldMaster copy() {
        return new ElshaThreefoldMaster(this);
    }
}
