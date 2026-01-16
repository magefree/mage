package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author muz
 */
public final class TheGhoulGunslinger extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another nontoken Zombie or Mutant you control");

    static {
        filter.add(Predicates.or(
                SubType.ZOMBIE.getPredicate(),
                SubType.MUTANT.getPredicate()
        ));
        filter.add(TokenPredicate.FALSE);
    }

    public TheGhoulGunslinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever The Ghoul or another nontoken Zombie or Mutant you control dies, target player gets two rad counters.
        // If that player is you, create a Treasure token.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(
            new TheGhoulGunslingerEffect(), false, filter
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private TheGhoulGunslinger(final TheGhoulGunslinger card) {
        super(card);
    }

    @Override
    public TheGhoulGunslinger copy() {
        return new TheGhoulGunslinger(this);
    }
}

class TheGhoulGunslingerEffect extends OneShotEffect {

    TheGhoulGunslingerEffect() {
        super(Outcome.GainLife);
        staticText = "target player gets two rad counters. If that player is you, create a Treasure token.";
    }

    private TheGhoulGunslingerEffect(final TheGhoulGunslingerEffect effect) {
        super(effect);
    }

    @Override
    public TheGhoulGunslingerEffect copy() {
        return new TheGhoulGunslingerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        targetPlayer.addCounters(CounterType.RAD.createInstance(2), source.getControllerId(), source, game);
        if (source.isControlledBy(targetPlayer.getId())) {
            Token token = new TreasureToken();
            token.putOntoBattlefield(1, game, source, source.getControllerId());
        }
        return true;
    }
}
