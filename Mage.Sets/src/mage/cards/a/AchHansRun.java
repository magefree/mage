
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardRepository;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class AchHansRun extends CardImpl {

    public AchHansRun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}{G}{G}");

        // At the beginning of your upkeep, you may say "Ach! Hans, run! It’s the …" and the name of a creature card. If you do, search your library for a card with that name, put it onto the battlefield, then shuffle your library. That creature gains haste. Exile it at the beginning of the next end step.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AchHansRunEffect(), TargetController.YOU, true));
    }

    public AchHansRun(final AchHansRun card) {
        super(card);
    }

    @Override
    public AchHansRun copy() {
        return new AchHansRun(this);
    }
}

class AchHansRunEffect extends OneShotEffect {

    public AchHansRunEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may say \"Ach! Hans, run! It’s the …\" and the name of a creature card. If you do, search your library for a card with that name, put it onto the battlefield, then shuffle your library. That creature gains haste. Exile it at the beginning of the next end step";
    }

    public AchHansRunEffect(final AchHansRunEffect effect) {
        super(effect);
    }

    @Override
    public AchHansRunEffect copy() {
        return new AchHansRunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ChoiceImpl cardChoice = new ChoiceImpl(true);
            cardChoice.setChoices(CardRepository.instance.getCreatureNames());
            cardChoice.setMessage("Choose a creature card name");
            if (controller.choose(Outcome.Detriment, cardChoice, game)) {
                String cardName = cardChoice.getChoice();
                if (!game.isSimulation()) {
                    game.informPlayers(controller.getLogName() + ": \"Ach! Hans, run! It's the " + cardName + "!\"");
                }
                FilterCard nameFilter = new FilterCard();
                nameFilter.add(new NamePredicate(cardName));
                TargetCardInLibrary target = new TargetCardInLibrary(1, 1, nameFilter);
                if (controller.searchLibrary(target, game)) {
                    Card card = controller.getLibrary().remove(target.getFirstTarget(), game);
                    if (card != null) {
                        if (card != null && controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                            Permanent creature = game.getPermanent(card.getId());
                            if (creature != null) {
                                // gains haste
                                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                                effect.setTargetPointer(new FixedTarget(creature, game));
                                game.addEffect(effect, source);
                                // Exile at begin of next end step
                                ExileTargetEffect exileEffect = new ExileTargetEffect(null, null, Zone.BATTLEFIELD);
                                exileEffect.setTargetPointer(new FixedTarget(creature, game));
                                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                                game.addDelayedTriggeredAbility(delayedAbility, source);
                            }
                        }
                    }
                    controller.shuffleLibrary(source, game);
                }
                return true;
            }
        }
        return false;
    }
}
