package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StillnessInMotion extends CardImpl {

    public StillnessInMotion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // At the beginning of your upkeep, mill three cards. Then if you have no cards in your library, exile this enchantment and put five cards from your graveyard on top of your library in any order.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new StillnessInMotionEffect());
        this.addAbility(ability);
    }

    private StillnessInMotion(final StillnessInMotion card) {
        super(card);
    }

    @Override
    public StillnessInMotion copy() {
        return new StillnessInMotion(this);
    }
}

class StillnessInMotionEffect extends OneShotEffect {

    StillnessInMotionEffect() {
        super(Outcome.Benefit);
        staticText = "Then if your library has no cards in it, exile this enchantment " +
                "and put five cards from your graveyard on top of your library in any order";
    }

    private StillnessInMotionEffect(final StillnessInMotionEffect effect) {
        super(effect);
    }

    @Override
    public StillnessInMotionEffect copy() {
        return new StillnessInMotionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getLibrary().hasCards()) {
            return false;
        }
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(permanent -> player.moveCards(permanent, Zone.EXILED, source, game));
        int graveCount = Math.min(player.getGraveyard().size(), 5);
        if (graveCount < 1) {
            return true;
        }
        TargetCard target = new TargetCardInYourGraveyard(graveCount);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        player.putCardsOnTopOfLibrary(new CardsImpl(target.getTargets()), game, source, true);
        return true;
    }
}
