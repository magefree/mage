
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class NicolBolasTheDeceiver extends CardImpl {

    public NicolBolasTheDeceiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{5}{U}{B}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOLAS);
        this.setStartingLoyalty(5);

        // +3: Each opponent loses 3 life unless that player sacrifices a nonland permanent or discards a card.
        this.addAbility(new LoyaltyAbility(new NicolBolasTheDeceiverFirstEffect(), 3));
        // -3: Destroy target creature.  Draw a card.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
        // -11: Nicol Bolas, the Deceiver deals 7 damage to each opponent.  You draw 7 cards.
        ability = new LoyaltyAbility(new DamagePlayersEffect(7, TargetController.OPPONENT), -11);
        ability.addEffect(new DrawCardSourceControllerEffect(7));
        this.addAbility(ability);

    }

    private NicolBolasTheDeceiver(final NicolBolasTheDeceiver card) {
        super(card);
    }

    @Override
    public NicolBolasTheDeceiver copy() {
        return new NicolBolasTheDeceiver(this);
    }
}

class NicolBolasTheDeceiverFirstEffect extends OneShotEffect {

    public NicolBolasTheDeceiverFirstEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 3 life unless that player sacrifices a nonland permanent or discards a card";
    }

    public NicolBolasTheDeceiverFirstEffect(final NicolBolasTheDeceiverFirstEffect effect) {
        super(effect);
    }

    @Override
    public NicolBolasTheDeceiverFirstEffect copy() {
        return new NicolBolasTheDeceiverFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID opponentId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    int permanents = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_NON_LAND, opponent.getId(), game);
                    if (permanents > 0 && opponent.chooseUse(outcome, "Sacrifices a nonland permanent?",
                            "Otherwise you have to discard a card or lose 3 life.", "Sacrifice", "Discard or life loss", source, game)) {
                        Target target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND);
                        if (opponent.choose(outcome, target, source, game)) {
                            Permanent permanent = game.getPermanent(target.getFirstTarget());
                            if (permanent != null) {
                                permanent.sacrifice(source, game);
                                return true;
                            }
                        }
                    }
                    if (!opponent.getHand().isEmpty() && opponent.chooseUse(outcome, "Discard a card?",
                            "Otherwise you lose 3 life.", "Discard", "Lose 3 life", source, game)) {
                        opponent.discardOne(false, false, source, game);
                        return true;
                    }
                    opponent.loseLife(3, game, source, false);

                }
            }
            return true;
        }

        return false;

    }
}
