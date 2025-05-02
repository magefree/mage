package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Knight33Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardedHeir extends CardImpl {

    public GuardedHeir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When this creature enters, create two 3/3 white Knight creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Knight33Token(), 2)));
    }

    private GuardedHeir(final GuardedHeir card) {
        super(card);
    }

    @Override
    public GuardedHeir copy() {
        return new GuardedHeir(this);
    }
}
