package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadReckoning extends CardImpl {

    public DeadReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // You may put target creature card from your graveyard on top of your library. If you do, Dead Reckoning deals damage equal to that card's power to target creature.
        this.getSpellAbility().addEffect(new DeadReckoningEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DeadReckoning(final DeadReckoning card) {
        super(card);
    }

    @Override
    public DeadReckoning copy() {
        return new DeadReckoning(this);
    }
}

class DeadReckoningEffect extends OneShotEffect {

    public DeadReckoningEffect() {
        super(Outcome.Damage);
        this.staticText = "you may put target creature card from your graveyard on top of your library. " +
                "If you do, {this} deals damage equal to that card's power to target creature";
        this.setTargetPointer(new EachTargetPointer());
    }

    public DeadReckoningEffect(final DeadReckoningEffect effect) {
        super(effect);
    }

    @Override
    public DeadReckoningEffect copy() {
        return new DeadReckoningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        List<UUID> targets = this.getTargetPointer().getTargets(game, source);
        Card card = game.getCard(targets.get(0));
        if (player == null || card == null || !player.chooseUse(
                outcome, "Put " + card.getName() + " on top of your library?", source, game
        )) {
            return false;
        }
        int power = card.getPower().getValue();
        player.putCardsOnTopOfLibrary(card, game, source, false);
        if (targets.size() < 2 || power < 1) {
            return true;
        }
        Permanent permanent = game.getPermanent(targets.get(1));
        if (permanent == null) {
            return true;
        }
        permanent.damage(power, source, game);
        return true;
    }
}
