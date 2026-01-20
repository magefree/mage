package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class RetchedWretch extends CardImpl {

    public RetchedWretch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When this creature dies, if it had a -1/-1 counter on it, return it to the battlefield under its owner's control and it loses all abilities.
        this.addAbility(new DiesSourceTriggeredAbility(new RetchedWretchReturnEffect())
                .withInterveningIf(RetchedWretchCondition.instance));
    }

    private RetchedWretch(final RetchedWretch card) {
        super(card);
    }

    @Override
    public RetchedWretch copy() {
        return new RetchedWretch(this);
    }
}

enum RetchedWretchCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "permanentLeftBattlefield", Permanent.class)
                .filter(permanent -> permanent.getCounters(game).containsKey(CounterType.M1M1))
                .isPresent();
    }

    @Override
    public String toString() {
        return "it had a -1/-1 counter on it";
    }
}

class RetchedWretchReturnEffect extends OneShotEffect {

     RetchedWretchReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return it to the battlefield under its owner's control and it loses all abilities";
    }

    private RetchedWretchReturnEffect(final RetchedWretchReturnEffect effect) {
        super(effect);
    }

    @Override
    public RetchedWretchReturnEffect copy() {
        return new RetchedWretchReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        Player player = game.getPlayer(card.getOwnerId());
        if (player == null) {
            return false;
        }
        if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
            Permanent permanent = game.getPermanent(card.getId());
            if (permanent != null) {
                ContinuousEffect effect = new LoseAllAbilitiesTargetEffect(Duration.Custom);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }

}
