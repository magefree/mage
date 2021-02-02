
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author LevelX2
 */
public final class DragonlairSpider extends CardImpl {

    public DragonlairSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}{G}{G}");
        this.subtype.add(SubType.SPIDER);


        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever an opponent casts a spell, create a 1/1 green Insect creature token.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new CreateTokenEffect(new InsectToken()), false));


    }

    private DragonlairSpider(final DragonlairSpider card) {
        super(card);
    }

    @Override
    public DragonlairSpider copy() {
        return new DragonlairSpider(this);
    }
}
