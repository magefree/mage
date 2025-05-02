package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EagerTrufflesnout extends CardImpl {

    public EagerTrufflesnout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature deals combat damage to a player, create a Food token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new FoodToken())));
    }

    private EagerTrufflesnout(final EagerTrufflesnout card) {
        super(card);
    }

    @Override
    public EagerTrufflesnout copy() {
        return new EagerTrufflesnout(this);
    }
}
