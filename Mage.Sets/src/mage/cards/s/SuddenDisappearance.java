
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author BetaSteward
 */
public final class SuddenDisappearance extends CardImpl {

    public SuddenDisappearance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}");

        // Exile all nonland permanents target player controls. Return the exiled cards to the battlefield under their owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new SuddenDisappearanceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public SuddenDisappearance(final SuddenDisappearance card) {
        super(card);
    }

    @Override
    public SuddenDisappearance copy() {
        return new SuddenDisappearance(this);
    }
}

class SuddenDisappearanceEffect extends OneShotEffect {

    private static FilterNonlandPermanent filter = new FilterNonlandPermanent();

    public SuddenDisappearanceEffect() {
        super(Outcome.Exile);
        staticText = "Exile all nonland permanents target player controls. Return the exiled cards to the battlefield under their owner's control at the beginning of the next end step";
    }

    public SuddenDisappearanceEffect(final SuddenDisappearanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Set<Card> permsSet = new HashSet<>(game.getBattlefield().getAllActivePermanents(filter, source.getFirstTarget(), game));
            if (!permsSet.isEmpty()) {
                controller.moveCardsToExile(permsSet, source, game, true, source.getSourceId(), sourceObject.getIdName());
                Cards targets = new CardsImpl();
                for (Card card : permsSet) {
                    targets.add(card.getId());
                }
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect();
                effect.setText("Return the exiled cards to the battlefield under their owner's control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTargets(targets, game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
            }
            return true;
        }

        return false;
    }

    @Override
    public SuddenDisappearanceEffect copy() {
        return new SuddenDisappearanceEffect(this);
    }

}
