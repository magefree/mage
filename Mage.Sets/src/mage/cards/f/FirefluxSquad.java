package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirefluxSquad extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another attacking creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(AttackingPredicate.instance);
    }

    public FirefluxSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Fireflux Squad attacks, you may exile another target attacking creature you control. If you do, reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield tapped and attacking and the rest on the bottom of your library in a random order.
        Ability ability = new AttacksTriggeredAbility(new FirefluxSquadEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
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

class FirefluxSquadEffect extends OneShotEffect {

    FirefluxSquadEffect() {
        super(Outcome.Benefit);
        staticText = "exile another target attacking creature you control. If you do, " +
                "reveal cards from the top of your library until you reveal a creature card. " +
                "Put that card onto the battlefield tapped and attacking " +
                "and the rest on the bottom of your library in a random order.";
    }

    private FirefluxSquadEffect(final FirefluxSquadEffect effect) {
        super(effect);
    }

    @Override
    public FirefluxSquadEffect copy() {
        return new FirefluxSquadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.EXILED, source, game);
        Card toBattlefield = null;
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card != null && card.isCreature(game)) {
                toBattlefield = card;
                break;
            }
        }
        player.revealCards(source, cards, game);
        if (toBattlefield == null) {
            return player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, true, false, true, null);
        permanent = game.getPermanent(toBattlefield.getId());
        if (permanent != null) {
            cards.remove(toBattlefield);
            game.getCombat().addAttackingCreature(permanent.getId(), game);
        }
        return player.putCardsOnBottomOfLibrary(cards, game, source, false);
    }
}