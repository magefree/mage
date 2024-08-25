package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Epicenter extends CardImpl {

    private static final Condition condition = new InvertCondition(ThresholdCondition.instance);

    public Epicenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Target player sacrifices a land.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SacrificeEffect(StaticFilters.FILTER_LAND, 1, "Target player"),
                condition, "Target player sacrifices a land"
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Threshold - Each player sacrifices all lands they control instead if seven or more cards are in your graveyard.
        this.getSpellAbility().addEffect(new EpicenterEffect());
    }

    private Epicenter(final Epicenter card) {
        super(card);
    }

    @Override
    public Epicenter copy() {
        return new Epicenter(this);
    }
}

class EpicenterEffect extends OneShotEffect {

    EpicenterEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "<br>" + AbilityWord.THRESHOLD.formatWord() + "Each player sacrifices " +
                "all lands they control instead if seven or more cards are in your graveyard.";
    }

    private EpicenterEffect(final EpicenterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!ThresholdCondition.instance.apply(game, source)) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_LAND, source.getControllerId(), source, game
        )) {
            permanent.sacrifice(source, game);
        }
        return true;
    }

    @Override
    public EpicenterEffect copy() {
        return new EpicenterEffect(this);
    }
}
