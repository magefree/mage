package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Alexsandr0x
 */
public final class CitadelOfPain extends CardImpl {

    public CitadelOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of each player's end step, Citadel of Pain deals X damage to that player, where X is the number of untapped lands they control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.EACH_PLAYER, new CitadelOfPainEffect(), false
        ));
    }

    private CitadelOfPain(final CitadelOfPain card) {
        super(card);
    }

    @Override
    public CitadelOfPain copy() {
        return new CitadelOfPain(this);
    }
}

class CitadelOfPainEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public CitadelOfPainEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to that player, where X is the number of untapped lands they control.";
    }

    private CitadelOfPainEffect(final CitadelOfPainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        int damage = game.getBattlefield().count(filter, game.getActivePlayerId(), source, game);
        return damage > 0 && player.damage(damage, source.getSourceId(), source, game) > 0;
    }

    @Override
    public CitadelOfPainEffect copy() {
        return new CitadelOfPainEffect(this);
    }
}
