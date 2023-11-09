
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author LevelX2
 */
public final class RakshasaGravecaller extends CardImpl {

    public RakshasaGravecaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Rakshasa Gravecaller exploits a creature, create two 2/2 black Zombie creature tokens.
        this.addAbility(new ExploitCreatureTriggeredAbility(new CreateTokenEffect(new ZombieToken(), 2), false));
    }

    private RakshasaGravecaller(final RakshasaGravecaller card) {
        super(card);
    }

    @Override
    public RakshasaGravecaller copy() {
        return new RakshasaGravecaller(this);
    }
}
