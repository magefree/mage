package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author North
 */
public final class CruelUltimatum extends CardImpl {

    public CruelUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}{B}{B}{B}{R}{R}");

        // Target opponent sacrifices a creature, discards three cards, then loses 5 life.
        // You return a creature card from your graveyard to your hand, draw three cards, then gain 5 life.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                1, "Target opponent"
        ));
        this.getSpellAbility().addEffect(new DiscardTargetEffect(3).setText(", discards three cards"));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(5).setText(", then loses 5 life"));
        this.getSpellAbility().addEffect(new CruelUltimatumEffect());
    }

    private CruelUltimatum(final CruelUltimatum card) {
        super(card);
    }

    @Override
    public CruelUltimatum copy() {
        return new CruelUltimatum(this);
    }
}

class CruelUltimatumEffect extends OneShotEffect {

    public CruelUltimatumEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "You return a creature card from your graveyard "
                + "to your hand, draw three cards, then gain 5 life";
    }

    public CruelUltimatumEffect(final CruelUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public CruelUltimatumEffect copy() {
        return new CruelUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        controller.choose(Outcome.ReturnToHand, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            controller.moveCards(card, Zone.HAND, source, game);
        }
        controller.drawCards(3, source, game);
        controller.gainLife(5, game, source);
        return true;
    }
}
