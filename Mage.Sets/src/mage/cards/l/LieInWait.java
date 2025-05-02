package mage.cards.l;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author androosss
 */
public final class LieInWait extends CardImpl {

    public LieInWait(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{G}{U}");
        

        // Return target creature card from your graveyard to your hand. Lie in Wait deals damage equal to that card's power to target creature.
        this.getSpellAbility().addEffect(new LieInWaitTargetEffect());
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
        setTargetPointer(new EachTargetPointer());
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
        List<UUID> targets = getTargetPointer().getTargets(game, source);
        Card card = game.getCard(targets.get(0));
        if (card == null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        boolean result = card.moveToZone(Zone.HAND, source, game,false);
        if (result && targets.size() >= 2) {
            int power = card.getPower().getValue();
            Permanent permanent = game.getPermanent(targets.get(1));
            permanent.damage(power, source, game);       
        }
        return result;
    }
}
