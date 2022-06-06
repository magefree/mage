
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author fireshoes
 */
public final class NecromasterDragon extends CardImpl {

    public NecromasterDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{B}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Necromaster Dragon deals combat damage to a player, you may pay {2}. If you do, create a 2/2 black Zombie creature token and each opponent puts the top two cards of their library into their graveyard
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
            new DoIfCostPaid(new CreateTokenEffect(new ZombieToken(), 1), new ManaCostsImpl<>("{2}")), false);
        ability.addEffect(new MillCardsEachPlayerEffect(2, TargetController.OPPONENT));
        this.addAbility(ability);
    }

    private NecromasterDragon(final NecromasterDragon card) {
        super(card);
    }

    @Override
    public NecromasterDragon copy() {
        return new NecromasterDragon(this);
    }
}
