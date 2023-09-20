
package mage.cards.k;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class KillerInstinct extends CardImpl {

    public KillerInstinct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{G}");

        // At the beginning of your upkeep, reveal the top card of your library. If it's a creature card, put it onto the battlefield. That creature gains haste until end of turn. Sacrifice it at the beginning of the next end step.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new KillerInstinctEffect(), TargetController.YOU, false));
    }

    private KillerInstinct(final KillerInstinct card) {
        super(card);
    }

    @Override
    public KillerInstinct copy() {
        return new KillerInstinct(this);
    }
}

class KillerInstinctEffect extends OneShotEffect {

    KillerInstinctEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top card of your library. If it's a creature card, put it onto the battlefield. That creature gains haste until end of turn. Sacrifice it at the beginning of the next end step.";
    }

    private KillerInstinctEffect(final KillerInstinctEffect effect) {
        super(effect);
    }

    @Override
    public KillerInstinctEffect copy() {
        return new KillerInstinctEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (player == null || sourceObject == null) {
            return false;
        }
        Library library = player.getLibrary();
        if (library == null || !library.hasCards()) {
            return false;
        }
        Card card = library.getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
        if (card.isCreature(game) && player.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            Permanent permanent = game.getPermanent(card.getId());
            if (permanent != null) {
                FixedTarget ft = new FixedTarget(permanent, game);
                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                effect.setTargetPointer(ft);
                game.addEffect(effect, source);
                Effect sacrificeEffect = new SacrificeTargetEffect("Sacrifice it at the beginning of the next end step", source.getControllerId());
                sacrificeEffect.setTargetPointer(ft);
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
            }
            return true;
        }
        return false;
    }
}
