package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class DebtToTheKami extends CardImpl {

    public DebtToTheKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Choose one—
        // • Target opponent exiles a creature they control.
        this.getSpellAbility().addEffect(new DebtToTheKamiExileCreatureEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // • Target opponent exiles an enchantment they control.
        Mode mode = new Mode(new DebtToTheKamiExileEnchantmentEffect());
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    private DebtToTheKami(final DebtToTheKami card) {
        super(card);
    }

    @Override
    public DebtToTheKami copy() {
        return new DebtToTheKami(this);
    }
}

class DebtToTheKamiExileCreatureEffect extends OneShotEffect {

    public DebtToTheKamiExileCreatureEffect() {
        super(Outcome.Exile);
        this.staticText = "Target opponent exiles a creature they control";
    }

    private DebtToTheKamiExileCreatureEffect(final DebtToTheKamiExileCreatureEffect effect) {
        super(effect);
    }

    @Override
    public DebtToTheKamiExileCreatureEffect copy() {
        return new DebtToTheKamiExileCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return player.moveCards(permanent, Zone.EXILED, source, game);
    }
}

class DebtToTheKamiExileEnchantmentEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("enchantment you control");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public DebtToTheKamiExileEnchantmentEffect() {
        super(Outcome.Exile);
        this.staticText = "Target opponent exiles an enchantment they control";
    }

    private DebtToTheKamiExileEnchantmentEffect(final DebtToTheKamiExileEnchantmentEffect effect) {
        super(effect);
    }

    @Override
    public DebtToTheKamiExileEnchantmentEffect copy() {
        return new DebtToTheKamiExileEnchantmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        Target target = new TargetControlledPermanent(filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return player.moveCards(permanent, Zone.EXILED, source, game);
    }
}
