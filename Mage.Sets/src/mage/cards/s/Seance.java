package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author BetaSteward
 */
public final class Seance extends CardImpl {

    public Seance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of each upkeep, you may exile target creature card from your graveyard. If you do, create a token that's a copy of that card except it's a Spirit in addition to its other types. Exile it at the beginning of the next end step.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SeanceEffect(), TargetController.ANY, true);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability);
    }

    private Seance(final Seance card) {
        super(card);
    }

    @Override
    public Seance copy() {
        return new Seance(this);
    }
}

class SeanceEffect extends OneShotEffect {

    public SeanceEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may exile target creature card from your graveyard. If you do, create a token that's a copy of that card except it's a Spirit in addition to its other types. Exile it at the beginning of the next end step";
    }

    public SeanceEffect(final SeanceEffect effect) {
        super(effect);
    }

    @Override
    public SeanceEffect copy() {
        return new SeanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            controller.moveCards(card, Zone.EXILED, source, game); // Also if the move to exile is replaced, the copy takes place
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, false);
            effect.setTargetPointer(new FixedTarget(card, game));
            effect.setAdditionalSubType(SubType.SPIRIT);
            effect.apply(game, source);
            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }

        return false;
    }

}
