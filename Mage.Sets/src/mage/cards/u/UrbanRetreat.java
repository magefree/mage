package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrbanRetreat extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("tapped creature you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public UrbanRetreat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}, {W}, or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // {2}, Return a tapped creature you control to its owner's hand: Put this card from your hand onto the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.HAND, new UrbanRetreatEffect(), new GenericManaCost(2));
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent()));
        this.addAbility(ability);
    }

    private UrbanRetreat(final UrbanRetreat card) {
        super(card);
    }

    @Override
    public UrbanRetreat copy() {
        return new UrbanRetreat(this);
    }
}

class UrbanRetreatEffect extends OneShotEffect {

    UrbanRetreatEffect() {
        super(Outcome.Benefit);
        staticText = "put this card from your hand onto the battlefield";
    }

    private UrbanRetreatEffect(final UrbanRetreatEffect effect) {
        super(effect);
    }

    @Override
    public UrbanRetreatEffect copy() {
        return new UrbanRetreatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        return player != null && card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
