
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BatToken;

/**
 *
 * @author LevelX2
 */
public final class BelfrySpirit extends CardImpl {

    public BelfrySpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haunt
        // When Belfry Spirit enters the battlefield or the creature it haunts dies, create two 1/1 black Bat creature tokens with flying.
        Ability ability = new HauntAbility(this, new CreateTokenEffect(new BatToken(), 2));
        this.addAbility(ability);
    }

    private BelfrySpirit(final BelfrySpirit card) {
        super(card);
    }

    @Override
    public BelfrySpirit copy() {
        return new BelfrySpirit(this);
    }
}
