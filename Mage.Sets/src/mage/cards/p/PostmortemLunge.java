package mage.cards.p;

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
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class PostmortemLunge extends CardImpl {

    public PostmortemLunge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B/P}");

        // Return target creature card with converted mana cost X from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new PostmortemLungeEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().setTargetAdjuster(PostmortemLungeAdjuster.instance);
    }

    private PostmortemLunge(final PostmortemLunge card) {
        super(card);
    }

    @Override
    public PostmortemLunge copy() {
        return new PostmortemLunge(this);
    }
}

enum PostmortemLungeAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filter = new FilterCreatureCard("creature card with mana value " + xValue + " or less from your graveyard");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.getTargets().add(new TargetCardInYourGraveyard(filter));
    }
}

class PostmortemLungeEffect extends OneShotEffect {

    public PostmortemLungeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target creature card with mana value X from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step";
    }

    private PostmortemLungeEffect(final PostmortemLungeEffect effect) {
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
