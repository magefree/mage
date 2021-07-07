package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Monsoon extends CardImpl {

    public Monsoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{G}");

        // At the beginning of each player's end step, tap all untapped Islands that player controls and Monsoon deals X damage to the player, where X is the number of Islands tapped this way.
        TriggeredAbility ability = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of each player's end step", true, new MonsoonEffect());
        this.addAbility(ability);
    }

    private Monsoon(final Monsoon card) {
        super(card);
    }

    @Override
    public Monsoon copy() {
        return new Monsoon(this);
    }
}

class MonsoonEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.ISLAND.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public MonsoonEffect() {
        super(Outcome.Damage);
        this.staticText = "tap all untapped Islands that player controls and {this} deals X damage to the player, where X is the number of Islands tapped this way";
    }

    private MonsoonEffect(final MonsoonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            int damage = 0;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                permanent.tap(source, game);
                damage++;
            }
            player.damage(damage, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public MonsoonEffect copy() {
        return new MonsoonEffect(this);
    }
}
