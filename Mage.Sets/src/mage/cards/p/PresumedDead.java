package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class PresumedDead extends CardImpl {

    public PresumedDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Until end of turn, target creature gets +2/+0 and gains "When this creature dies, return it to the battlefield under its owner's control and suspect it."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setText("Until end of turn, target creature gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new DiesSourceTriggeredAbility(new PresumedDeadEffect(), false, SetTargetPointer.CARD))
                .setText("and gains \"When this creature dies, return it to the battlefield under its owner's control and suspect it.\""));

    }

    private PresumedDead(final PresumedDead card) {
        super(card);
    }

    @Override
    public PresumedDead copy() {
        return new PresumedDead(this);
    }
}

class PresumedDeadEffect extends OneShotEffect {

    PresumedDeadEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield under its owner's control and suspect it";
    }

    private PresumedDeadEffect(final PresumedDeadEffect effect) {
        super(effect);
    }

    @Override
    public PresumedDeadEffect copy() {
        return new PresumedDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (controller == null || cards.isEmpty()) {
            return false;
        }
        controller.moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
        game.processAction();
        for (Card card : cards.getCards(game)) {
            Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
            if (permanent != null) {
                permanent.setSuspected(true, game, source);
            }
        }
        return true;
    }

}
