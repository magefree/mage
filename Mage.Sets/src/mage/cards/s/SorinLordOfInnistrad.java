
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.command.emblems.SorinLordOfInnistradEmblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SorinLordOfInnistradVampireToken;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward
 */
public final class SorinLordOfInnistrad extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
    }

    public SorinLordOfInnistrad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SORIN);

        this.setStartingLoyalty(3);

        // +1: Create a 1/1 black Vampire creature token with lifelink.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SorinLordOfInnistradVampireToken()), 1));

        // -2: You get an emblem with "Creatures you control get +1/+0."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new SorinLordOfInnistradEmblem()), -2));

        // -6: Destroy up to three target creatures and/or other planeswalkers. Return each card put into a graveyard this way to the battlefield under your control.
        LoyaltyAbility ability = new LoyaltyAbility(new SorinLordOfInnistradEffect(), -6);
        ability.addTarget(new TargetPermanent(0, 3, filter, false));
        this.addAbility(ability);
    }

    private SorinLordOfInnistrad(final SorinLordOfInnistrad card) {
        super(card);
    }

    @Override
    public SorinLordOfInnistrad copy() {
        return new SorinLordOfInnistrad(this);
    }
}

class SorinLordOfInnistradEffect extends OneShotEffect {

    public SorinLordOfInnistradEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Destroy up to three target creatures and/or other planeswalkers. Return each card put into a graveyard this way to the battlefield under your control";
    }

    public SorinLordOfInnistradEffect(final SorinLordOfInnistradEffect effect) {
        super(effect);
    }

    @Override
    public SorinLordOfInnistradEffect copy() {
        return new SorinLordOfInnistradEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Card> toBattlefield = new HashSet<>();
        for (UUID targetId : source.getTargets().get(0).getTargets()) {
            Permanent perm = game.getPermanent(targetId);
            if (perm != null) {
                perm.destroy(source, game, false);
                if (Zone.GRAVEYARD == game.getState().getZone(targetId)) {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        toBattlefield.add(card);
                    }
                }
            }
        }
        game.getState().processAction(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
        }
        return false;
    }

}
