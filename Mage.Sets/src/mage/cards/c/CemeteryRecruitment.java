
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class CemeteryRecruitment extends CardImpl {

    public CemeteryRecruitment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Return target creature card from your graveyard to your hand. If it's a Zombie card, draw a card.
        this.getSpellAbility().addEffect(new CemeteryRecruitmentEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private CemeteryRecruitment(final CemeteryRecruitment card) {
        super(card);
    }

    @Override
    public CemeteryRecruitment copy() {
        return new CemeteryRecruitment(this);
    }
}

class CemeteryRecruitmentEffect extends OneShotEffect {

    public CemeteryRecruitmentEffect() {
        super(Outcome.Benefit);
        staticText = "Return target creature card from your graveyard to your hand. If it's a Zombie card, draw a card";
    }

    private CemeteryRecruitmentEffect(final CemeteryRecruitmentEffect effect) {
        super(effect);
    }

    @Override
    public CemeteryRecruitmentEffect copy() {
        return new CemeteryRecruitmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
                if (controller.moveCards(card, Zone.HAND, source, game)
                        && card.hasSubtype(SubType.ZOMBIE, game)) {
                    controller.drawCards(1, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
