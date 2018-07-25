package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZergToken;

/**
 *
 * @author NinthWorld
 */
public final class BroodLord extends CardImpl {

    public BroodLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Brood Lord attacks, put two 1/1 green Zerg creature tokens onto the battlefield tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new ZergToken(), 2, true, true), false));
    }

    public BroodLord(final BroodLord card) {
        super(card);
    }

    @Override
    public BroodLord copy() {
        return new BroodLord(this);
    }
}
