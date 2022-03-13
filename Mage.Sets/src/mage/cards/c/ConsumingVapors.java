package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class ConsumingVapors extends CardImpl {

    public ConsumingVapors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Target player sacrifices a creature. You gain life equal to that creature's toughness.
        this.getSpellAbility().addEffect(new ConsumingVaporsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Rebound (If you cast this spell from your hand, exile it as it resolves. At the beginning of your next upkeep, you may cast this card from exile without paying its mana cost.)
        this.addAbility(new ReboundAbility());
    }

    private ConsumingVapors(final ConsumingVapors card) {
        super(card);
    }

    @Override
    public ConsumingVapors copy() {
        return new ConsumingVapors(this);
    }
}

class ConsumingVaporsEffect extends OneShotEffect {

    ConsumingVaporsEffect() {
        super(Outcome.Sacrifice);
        staticText = "Target player sacrifices a creature. You gain life equal to that creature's toughness";
    }

    ConsumingVaporsEffect(ConsumingVaporsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());

        FilterControlledPermanent filter = new FilterControlledPermanent("creature");
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);

        if (player != null && target.canChoose(player.getId(), source, game)) {
            player.choose(Outcome.Sacrifice, target, source, game);

            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.sacrifice(source, game);
                controller.gainLife(permanent.getToughness().getValue(), game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public ConsumingVaporsEffect copy() {
        return new ConsumingVaporsEffect(this);
    }

}
