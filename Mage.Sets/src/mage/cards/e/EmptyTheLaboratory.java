package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmptyTheLaboratory extends CardImpl {

    public EmptyTheLaboratory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Sacrifice X Zombies, then reveal cards from the top of your library until you reveal a number of Zombie creature cards equal to the number of Zombies sacrificed this way. Put those cards onto the battlefield and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new EmptyTheLaboratoryEffect());
    }

    private EmptyTheLaboratory(final EmptyTheLaboratory card) {
        super(card);
    }

    @Override
    public EmptyTheLaboratory copy() {
        return new EmptyTheLaboratory(this);
    }
}

class EmptyTheLaboratoryEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ZOMBIE, "Zombies");
    private static final FilterCard filter2 = new FilterCreatureCard();

    static {
        filter2.add(SubType.ZOMBIE.getPredicate());
    }

    EmptyTheLaboratoryEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice X Zombies, then reveal cards from the top of your library until you reveal " +
                "a number of Zombie creature cards equal to the number of Zombies sacrificed this way. " +
                "Put those cards onto the battlefield and the rest on the bottom of your library in a random order";
    }

    private EmptyTheLaboratoryEffect(final EmptyTheLaboratoryEffect effect) {
        super(effect);
    }

    @Override
    public EmptyTheLaboratoryEffect copy() {
        return new EmptyTheLaboratoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int toSacrifice = Math.min(
                source.getManaCostsToPay().getX(),
                game.getBattlefield().count(
                        filter, source.getControllerId(), source, game
                )
        );
        if (toSacrifice < 1) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(toSacrifice, filter);
        target.setNotTarget(true);
        player.choose(Outcome.Sacrifice, target, source, game);
        int sacrificed = 0;
        for (UUID permanentId : target.getTargets()) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && permanent.sacrifice(source, game)) {
                sacrificed++;
            }
        }
        Cards toReveal = new CardsImpl();
        int zombies = 0;
        for (Card card : player.getLibrary().getCards(game)) {
            toReveal.add(card);
            if (card.isCreature(game) && card.hasSubtype(SubType.ZOMBIE, game)) {
                zombies++;
            }
            if (zombies >= sacrificed) {
                break;
            }
        }
        player.revealCards(source, toReveal, game);
        player.moveCards(toReveal.getCards(filter2, game), Zone.BATTLEFIELD, source, game);
        toReveal.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }
}
