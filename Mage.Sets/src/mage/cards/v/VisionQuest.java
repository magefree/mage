package mage.cards.v;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class VisionQuest extends CardImpl {

    public VisionQuest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{R}");

        // Search your library and/or graveyard for an artifact creature card with mana value X or less and put it onto
        // the battlefield with X additional +1/+1 counters on it. If X is 4 or greater, it gains haste until end of turn.
        // If you search your library this way, shuffle.
        this.getSpellAbility().addEffect(new VisionQuestEffect());
    }

    private VisionQuest(final VisionQuest card) {
        super(card);
    }

    @Override
    public VisionQuest copy() {
        return new VisionQuest(this);
    }
}

class VisionQuestEffect extends OneShotEffect {

    private static final FilterArtifactCard filter = new FilterArtifactCard("artifact creature card");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    VisionQuestEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Search your library and/or graveyard for an artifact creature card with mana value X or less " +
            "and put it onto the battlefield with X additional +1/+1 counters on it. If X is 4 or greater, " +
            "it gains haste until end of turn. If you search your library this way, shuffle";
    }

    private VisionQuestEffect(final VisionQuestEffect effect) {
        super(effect);
    }

    @Override
    public VisionQuestEffect copy() {
        return new VisionQuestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
        FilterArtifactCard xFilter = filter.copy();
        xFilter.add(new ManaValuePredicate(ComparisonType.OR_LESS, xValue));

        Card card = null;
        boolean searched = false;
        if (controller.chooseUse(
                outcome, "Search your library for an artifact creature card with mana value X or less?", source, game
        )) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 1, xFilter);
            if (controller.searchLibrary(target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
            searched = true;
        }

        if (card == null
            && controller.chooseUse(
                outcome, "Search your graveyard for an artifact creature card with mana value X or less?",
                source, game
        )) {
            TargetCard target = new TargetCardInYourGraveyard(0, 1, xFilter, true);
            if (controller.choose(outcome, controller.getGraveyard(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }

        if (card != null) {
            if (xValue > 0) {
                game.setEnterWithCounters(
                    card.getId(),
                    new Counters().addCounter(CounterType.P1P1.createInstance(xValue))
                );
            }
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
            if (permanent != null && xValue >= 4) {
                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance());
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
        }

        if (searched) {
            controller.shuffleLibrary(source, game);
        }
        return true;
    }
}
