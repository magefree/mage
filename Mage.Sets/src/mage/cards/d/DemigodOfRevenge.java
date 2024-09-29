
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DemigodOfRevenge extends CardImpl {

    public DemigodOfRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B/R}{B/R}{B/R}{B/R}{B/R}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When you cast Demigod of Revenge, return all cards named Demigod of Revenge from your graveyard to the battlefield.
        this.addAbility(new CastSourceTriggeredAbility(new DemigodOfRevengeReturnEffect()));
    }

    private DemigodOfRevenge(final DemigodOfRevenge card) {
        super(card);
    }

    @Override
    public DemigodOfRevenge copy() {
        return new DemigodOfRevenge(this);
    }
}

class DemigodOfRevengeReturnEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new NamePredicate("Demigod of Revenge"));
    }

    public DemigodOfRevengeReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return all cards named Demigod of Revenge from your graveyard to the battlefield";
    }

    private DemigodOfRevengeReturnEffect(final DemigodOfRevengeReturnEffect effect) {
        super(effect);
    }

    @Override
    public DemigodOfRevengeReturnEffect copy() {
        return new DemigodOfRevengeReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.moveCards(controller.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
        }
        return false;
    }
}
