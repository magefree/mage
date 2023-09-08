
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class DawnOfTheDead extends CardImpl {

    public DawnOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}{B}");

        // At the beginning of your upkeep, you lose 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(1), TargetController.YOU, false));

        // At the beginning of your upkeep, you may return target creature card from your graveyard to the battlefield.
        // That creature gains haste until end of turn. Exile it at the beginning of the next end step.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DawnOfTheDeadEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private DawnOfTheDead(final DawnOfTheDead card) {
        super(card);
    }

    @Override
    public DawnOfTheDead copy() {
        return new DawnOfTheDead(this);
    }
}

class DawnOfTheDeadEffect extends OneShotEffect {

    public DawnOfTheDeadEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return target creature card from your graveyard to the battlefield. That creature gains haste until end of turn. Exile it at the beginning of the next end step";
    }

    private DawnOfTheDeadEffect(final DawnOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public DawnOfTheDeadEffect copy() {
        return new DawnOfTheDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
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
            return true;
        }
        return false;
    }
}
