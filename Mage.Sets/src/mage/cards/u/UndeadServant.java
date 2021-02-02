
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author fireshoes
 */
public final class UndeadServant extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Undead Servant");

    static {
        filter.add(new NamePredicate("Undead Servant"));
    }

    public UndeadServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Undead Servant enters the battlefield, create a 2/2 black Zombie creature token for each card named Undead Servant in your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieToken(), new CardsInControllerGraveyardCount(filter))));
    }

    private UndeadServant(final UndeadServant card) {
        super(card);
    }

    @Override
    public UndeadServant copy() {
        return new UndeadServant(this);
    }
}
