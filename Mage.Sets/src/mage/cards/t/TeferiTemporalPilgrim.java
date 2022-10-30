package mage.cards.t;

import java.util.HashSet;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritTeferiToken;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class TeferiTemporalPilgrim extends CardImpl {

    public TeferiTemporalPilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);
        this.setStartingLoyalty(4);

        // Whenever you draw a card, put a loyalty counter on Teferi, Temporal Pilgrim.
        this.addAbility(new DrawCardControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance()), false));

        // 0: Draw a card.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 0));

        // -2: Create a 2/2 blue Spirit creature token with vigilance and "Whenever you draw a card, put a +1/+1 counter on this creature."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SpiritTeferiToken()), -2));

        // -12: Target opponent chooses a permanent they control and returns it to its owner's had. Then they shuffle each nonland permanent they control into its owner's library.
        Ability ability = new LoyaltyAbility(new TeferiTemporalPilgrimEffect(), -12);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TeferiTemporalPilgrim(final TeferiTemporalPilgrim card) {
        super(card);
    }

    @Override
    public TeferiTemporalPilgrim copy() {
        return new TeferiTemporalPilgrim(this);
    }
}

class TeferiTemporalPilgrimEffect extends OneShotEffect {

    public TeferiTemporalPilgrimEffect() {
        super(Outcome.Removal);
        this.staticText = "Target opponent chooses a permanent they control and returns it to its owner's had. Then they shuffle each nonland permanent they control into its owner's library.";
    }

    private TeferiTemporalPilgrimEffect(final TeferiTemporalPilgrimEffect effect) {
        super(effect);
    }

    @Override
    public TeferiTemporalPilgrimEffect copy() {
        return new TeferiTemporalPilgrimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        TargetControlledPermanent target = new TargetControlledPermanent();
        target.setNotTarget(true);
        opponent.chooseTarget(Outcome.ReturnToHand, target, source, game);
        Permanent toHand = game.getPermanent(target.getFirstTarget());
        if (toHand != null) {
            opponent.moveCards(toHand, Zone.HAND, source, game);
            game.getState().processAction(game);
        }
        HashSet<Permanent> toLibrary = new HashSet<>(game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_NON_LAND, opponent.getId(), game));
        if (toLibrary.isEmpty()) {
            return true;
        }
        HashSet<UUID> ownerIds = new HashSet<>();
        for (Permanent permanent : toLibrary) {
            ownerIds.add(permanent.getOwnerId());
        }
        opponent.moveCards(toLibrary, Zone.LIBRARY, source, game);
        for (UUID ownerId : ownerIds) {
            Player owner = game.getPlayer(ownerId);
            if (owner != null) {
                owner.shuffleLibrary(source, game);
            }
        }
        return true;
    }
}
