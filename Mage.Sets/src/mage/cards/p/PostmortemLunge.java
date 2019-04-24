
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 * @author North
 */
public final class PostmortemLunge extends CardImpl {

    public PostmortemLunge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B/P}");

        // Return target creature card with converted mana cost X from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new PostmortemLungeEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    public PostmortemLunge(final PostmortemLunge card) {
        super(card);
    }

    @Override
    public PostmortemLunge copy() {
        return new PostmortemLunge(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getAbilityType() == AbilityType.SPELL) { // otherwise the target is also added to the delayed triggered ability
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            FilterCard filter = new FilterCreatureCard("creature card with converted mana cost " + xValue + " or less from your graveyard");
            filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xValue + 1));
            ability.getTargets().add(new TargetCardInYourGraveyard(filter));
        }
    }
}

class PostmortemLungeEffect extends OneShotEffect {

    public PostmortemLungeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target creature card with converted mana cost X from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step";
    }

    public PostmortemLungeEffect(final PostmortemLungeEffect effect) {
        super(effect);
    }

    @Override
    public PostmortemLungeEffect copy() {
        return new PostmortemLungeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());

        if (card != null) {
            Player cardOwner = game.getPlayer(card.getOwnerId());
            if (cardOwner == null) {
                return false;
            }

            if (cardOwner.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                    ExileTargetEffect exileEffect = new ExileTargetEffect(null, null, Zone.BATTLEFIELD);
                    exileEffect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
                }
            }
            return true;
        }

        return false;
    }
}
