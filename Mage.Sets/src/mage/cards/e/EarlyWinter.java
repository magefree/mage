package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EarlyWinter extends CardImpl {

    public EarlyWinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");


        // Choose one —
        // * Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Target opponent exiles an enchantment they control.
        Mode mode = new Mode(new EarlyWinterTargetEffect());
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    private EarlyWinter(final EarlyWinter card) {
        super(card);
    }

    @Override
    public EarlyWinter copy() {
        return new EarlyWinter(this);
    }
}

class EarlyWinterTargetEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("enchantment you control");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public EarlyWinterTargetEffect() {
        super(Outcome.Exile);
        this.staticText = "Target opponent exiles an enchantment they control";
    }

    private EarlyWinterTargetEffect(final EarlyWinterTargetEffect effect) {
        super(effect);
    }

    @Override
    public EarlyWinterTargetEffect copy() {
        return new EarlyWinterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        Target target = new TargetControlledPermanent(filter);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return player.moveCards(permanent, Zone.EXILED, source, game);
    }
}
