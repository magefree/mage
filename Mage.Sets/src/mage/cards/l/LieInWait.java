package mage.cards.l;

import java.util.List;
import java.util.UUID;

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

/**
 *
 * @author androosss
 */
public final class LieInWait extends CardImpl {

    public LieInWait(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{G}{U}");
        

        // Return target creature card from your graveyard to your hand. Lie in Wait deals damage equal to that card's power to target creature.
        this.getSpellAbility().addEffect(new LieInWaitTargetEffect().setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    private LieInWait(final LieInWait card) {
        super(card);
    }

    @Override
    public LieInWait copy() {
        return new LieInWait(this);
    }

}

class LieInWaitTargetEffect extends OneShotEffect {

    LieInWaitTargetEffect() {
        super(Outcome.Benefit);
        staticText = "Return target creature card from your graveyard to your hand. "
                + "{this} deals damage equal to that card's power to target creature";
    }

    private LieInWaitTargetEffect(final LieInWaitTargetEffect effect) {
        super(effect);
    }

    @Override
    public LieInWaitTargetEffect copy() {
        return new LieInWaitTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        int power = card.getPower().getBaseValue();
        List<UUID> targets = getTargetPointer().getTargets(game, source);
        boolean result = new ReturnFromGraveyardToHandTargetEffect()
                .setTargetPointer(new FixedTarget(card.getId(), game))
                .apply(game, source);
        if (targets.size() >= 2) {
            result |= new DamageTargetEffect(power)
                        .setTargetPointer(new FixedTarget(targets.get(1), game))
                        .apply(game, source);
        }
        return result;
    }
}
