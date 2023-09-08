
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class FoulRenewal extends CardImpl {

    public FoulRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Return target creature card from your graveyard to your hand. Target creature gets -X/-X until end of turn, where X is the toughness of the card returned this way.
        this.getSpellAbility().addEffect(new FoulRenewalEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FoulRenewal(final FoulRenewal card) {
        super(card);
    }

    @Override
    public FoulRenewal copy() {
        return new FoulRenewal(this);
    }
}

class FoulRenewalEffect extends OneShotEffect {

    public FoulRenewalEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return target creature card from your graveyard to your hand. Target creature gets -X/-X until end of turn, where X is the toughness of the card returned this way";
    }

    private FoulRenewalEffect(final FoulRenewalEffect effect) {
        super(effect);
    }

    @Override
    public FoulRenewalEffect copy() {
        return new FoulRenewalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
                int xValue = card.getToughness().getValue() * -1;
                controller.moveCards(card, Zone.HAND, source, game);
                if (xValue != 0) {
                    ContinuousEffect effect = new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(source.getTargets().get(1).getFirstTarget()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
