package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeticulousExcavation extends CardImpl {

    public MeticulousExcavation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // {2}{W}: Return target permanent you control to its owner's hand. If it has unearth, instead exile it, then return that card to its owner's hand. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new MeticulousExcavationEffect(),
                new ManaCostsImpl<>("{2}{W}"), MyTurnCondition.instance
        );
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private MeticulousExcavation(final MeticulousExcavation card) {
        super(card);
    }

    @Override
    public MeticulousExcavation copy() {
        return new MeticulousExcavation(this);
    }
}

class MeticulousExcavationEffect extends OneShotEffect {

    MeticulousExcavationEffect() {
        super(Outcome.Benefit);
        staticText = "return target permanent you control to its owner's hand. " +
                "If it has unearth, instead exile it, then return that card to its owner's hand";
    }

    private MeticulousExcavationEffect(final MeticulousExcavationEffect effect) {
        super(effect);
    }

    @Override
    public MeticulousExcavationEffect copy() {
        return new MeticulousExcavationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        if (!permanent.getAbilities(game).containsClass(UnearthAbility.class)) {
            return player.moveCards(permanent, Zone.HAND, source, game);
        }
        player.moveCards(permanent, Zone.EXILED, source, game);
        return player.moveCards(game.getCard(permanent.getMainCard().getId()), Zone.HAND, source, game);
    }
}
