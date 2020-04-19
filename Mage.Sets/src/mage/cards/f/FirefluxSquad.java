package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class FirefluxSquad extends CardImpl {

    public FirefluxSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Fireflux Squad attacks, you may exile another target attacking creature you control. If you do, reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield tapped and attacking and the rest on the bottom of your library in a random order.
        Ability ability = new AttacksTriggeredAbility(new FireFluxSquadEffect(), true);

        FilterAttackingCreature filter = new FilterAttackingCreature("another target attacking creature you control");
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());

        ability.addTarget(new TargetAttackingCreature(1,1,filter,false));
        this.addAbility(ability);
    }

    private FirefluxSquad(final FirefluxSquad card) {
        super(card);
    }

    @Override
    public FirefluxSquad copy() {
        return new FirefluxSquad(this);
    }
}

class FireFluxSquadEffect extends OneShotEffect {

    public FireFluxSquadEffect() {
        super(Outcome.Detriment);
        staticText = "exile another target attacking creature you control. If you do, reveal cards from the top of your library until you reveal a creature card. " +
                "Put that card onto the battlefield tapped and attacking and the rest on the bottom of your library in a random order";
    }

    public FireFluxSquadEffect(final FireFluxSquadEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && targetPermanent != null) {
            Library library = controller.getLibrary();
            if (controller.moveCards(targetPermanent, Zone.EXILED, source, game) && library.hasCards()) {
                Cards cards = new CardsImpl();
                Card toBattlefield = null;
                for (Card card : library.getCards(game)) {
                    cards.add(card);
                    if (card.isCreature()) {
                        toBattlefield = card;
                        break;
                    }
                }
                if (toBattlefield != null) {
                    controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, true, false, false, null);
                    Permanent permanent = game.getPermanent(toBattlefield.getId());
                    if (permanent == null) {
                        return false;
                    }
                    game.getCombat().addAttackingCreature(permanent.getId(), game);
                }
                controller.revealCards(source, cards, game);
                cards.remove(toBattlefield);
                if (!cards.isEmpty()) {
                    controller.putCardsOnBottomOfLibrary(cards, game, source, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public FireFluxSquadEffect copy() {
        return new FireFluxSquadEffect(this);
    }
}
