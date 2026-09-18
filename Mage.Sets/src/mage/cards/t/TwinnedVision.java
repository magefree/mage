package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author muz
 */
public final class TwinnedVision extends CardImpl {

    public TwinnedVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U/R}");

        // Draw a card. If this spell wasn't cast from your hand, draw two cards instead.
        this.getSpellAbility().addEffect(new TwinnedVisionEffect());
        this.getSpellAbility().addWatcher(new CastFromHandWatcher());

        // Flashback--{1}{U/R}{U/R}, Discard a card.
        this.addAbility(new FlashbackAbility(this, new CompositeCost(
            new ManaCostsImpl<>("{1}{U/R}{U/R}"), new DiscardCardCost(), "{1}{U/R}{U/R}, discard a card"
        )));
    }

    private TwinnedVision(final TwinnedVision card) {
        super(card);
    }

    @Override
    public TwinnedVision copy() {
        return new TwinnedVision(this);
    }
}

class TwinnedVisionEffect extends OneShotEffect {

    TwinnedVisionEffect() {
        super(Outcome.DrawCard);
        staticText = "draw a card. If this spell wasn't cast from your hand, draw two cards instead";
    }

    private TwinnedVisionEffect(final TwinnedVisionEffect effect) {
        super(effect);
    }

    @Override
    public TwinnedVisionEffect copy() {
        return new TwinnedVisionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CastFromHandWatcher watcher = game.getState().getWatcher(CastFromHandWatcher.class);
        int amount = watcher != null && watcher.spellWasCastFromHand(source.getSourceId()) ? 1 : 2;
        return new DrawCardSourceControllerEffect(amount).apply(game, source);
    }
}
