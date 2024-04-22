package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ChaoticBacklash extends CardImpl {

    public ChaoticBacklash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");


        // Chaotic Backlash deals damage to target player equal to twice the number of white and/or blue permanents they control.
        this.getSpellAbility().addEffect(new ChaoticBacklashEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private ChaoticBacklash(final ChaoticBacklash card) {
        super(card);
    }

    @Override
    public ChaoticBacklash copy() {
        return new ChaoticBacklash(this);
    }
}

class ChaoticBacklashEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("white and/or blue permanents they control");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE)));
    }

    public ChaoticBacklashEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} deals damage to target player equal to twice the number of white and/or blue permanents they control";
    }

    private ChaoticBacklashEffect(final ChaoticBacklashEffect effect) {
        super(effect);
    }

    @Override
    public ChaoticBacklashEffect copy() {
        return new ChaoticBacklashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            int amount = 2 * game.getBattlefield().countAll(filter, targetPlayer.getId(), game);
            targetPlayer.damage(amount, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }
}