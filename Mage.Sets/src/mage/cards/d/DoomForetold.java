package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KnightToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoomForetold extends CardImpl {

    public DoomForetold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{B}");

        // At the beginning of each player's upkeep, that player sacrifices a nonland, nontoken permanent. If that player can't, they discard a card, they lose 2 life, you draw a card, you gain 2 life, you create a 2/2 white Knight creature token with vigilance, then you sacrifice Doom Foretold.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoomForetoldEffect(), TargetController.ACTIVE, false
        ));
    }

    private DoomForetold(final DoomForetold card) {
        super(card);
    }

    @Override
    public DoomForetold copy() {
        return new DoomForetold(this);
    }
}

class DoomForetoldEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland, nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    private static final Effect effect1 = new CreateTokenEffect(new KnightToken());
    private static final Effect effect2 = new SacrificeSourceEffect();

    DoomForetoldEffect() {
        super(Outcome.Benefit);
        staticText = "that player sacrifices a nonland, nontoken permanent. " +
                "If that player can't, they discard a card, they lose 2 life, you draw a card, you gain 2 life, " +
                "you create a 2/2 white Knight creature token with vigilance, then you sacrifice {this}";
    }

    private DoomForetoldEffect(final DoomForetoldEffect effect) {
        super(effect);
    }

    @Override
    public DoomForetoldEffect copy() {
        return new DoomForetoldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(game.getActivePlayerId());
        if (controller == null || player == null) {
            return false;
        }
        FilterPermanent filter2 = filter.copy();
        filter2.add(new ControllerIdPredicate(player.getId()));
        if (game.getBattlefield().contains(filter2, source, game, 1)) {
            TargetPermanent target = new TargetPermanent(filter2);
            target.setNotTarget(true);
            if (player.choose(Outcome.Sacrifice, target, source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null && permanent.sacrifice(source, game)) {
                    return true;
                }
            }
        }
        player.discard(1, false, false, source, game);
        player.loseLife(2, game, source, false);
        controller.drawCards(1, source, game);
        controller.gainLife(2, game, source);
        effect1.apply(game, source);
        effect2.apply(game, source);
        return true;
    }
}