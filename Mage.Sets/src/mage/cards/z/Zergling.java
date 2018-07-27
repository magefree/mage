package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZergToken;

/**
 *
 * @author NinthWorld
 */
public final class Zergling extends CardImpl {

    public Zergling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Zergling dies, put two 1/1 green Zerg creature tokens onto the battlefield.
        this.addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new ZergToken(), 2)));
    }

    public Zergling(final Zergling card) {
        super(card);
    }

    @Override
    public Zergling copy() {
        return new Zergling(this);
    }
}
