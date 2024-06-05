package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.EachTargetPointer;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class PyreticRebirth extends CardImpl {

    public PyreticRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{R}");

        // Return target artifact or creature card from your graveyard to your hand. Pyretic Rebirth deals damage equal to that card's mana value to up to one target creature or planeswalker.
        this.getSpellAbility().addEffect(new PyreticRebirthTargetEffect().setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER));
    }

    private PyreticRebirth(final PyreticRebirth card) {
        super(card);
    }

    @Override
    public PyreticRebirth copy() {
        return new PyreticRebirth(this);
    }
}

class PyreticRebirthTargetEffect extends OneShotEffect {

    PyreticRebirthTargetEffect() {
        super(Outcome.Benefit);
        staticText = "Return target artifact or creature card from your graveyard to your hand. "
                + "{this} deals damage equal to that card's mana value to up to one target creature or planeswalker";
    }

    private PyreticRebirthTargetEffect(final PyreticRebirthTargetEffect effect) {
        super(effect);
    }

    @Override
    public PyreticRebirthTargetEffect copy() {
        return new PyreticRebirthTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        int mv = card.getManaValue();
        List<UUID> targets = getTargetPointer().getTargets(game, source);
        boolean result = new ReturnFromGraveyardToHandTargetEffect()
                .setTargetPointer(new FixedTarget(card.getId(), game))
                .apply(game, source);
        if (targets.size() >= 2) {
            result |= new DamageTargetEffect(mv)
                    .setTargetPointer(new FixedTarget(targets.get(1), game))
                    .apply(game, source);
        }
        return result;
    }

}