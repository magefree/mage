
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class BatwingBrume extends CardImpl {

    public BatwingBrume(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W/B}");


        // Prevent all combat damage that would be dealt this turn if {W} was spent to cast Batwing Brume. Each player loses 1 life for each attacking creature they control if {B} was spent to cast Batwing Brume.
        Effect effect = new ConditionalReplacementEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true),
                new LockedInCondition(ManaWasSpentCondition.WHITE));
        effect.setText("Prevent all combat damage that would be dealt this turn if {W} was spent to cast this spell");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new BatwingBrumeEffect(),
                ManaWasSpentCondition.BLACK, "Each player loses 1 life for each attacking creature they control if {B} was spent to cast this spell"));
        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {W}{B} was spent.)</i>"));

    }

    private BatwingBrume(final BatwingBrume card) {
        super(card);
    }

    @Override
    public BatwingBrume copy() {
        return new BatwingBrume(this);
    }
}

class BatwingBrumeEffect extends OneShotEffect {

    public BatwingBrumeEffect() {
        super(Outcome.LoseLife);
    }

    public BatwingBrumeEffect(final BatwingBrumeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                final int amount = game.getBattlefield().getAllActivePermanents(new FilterAttackingCreature(), playerId, game).size();
                if (amount > 0) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.loseLife(amount, game, source, false);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public BatwingBrumeEffect copy() {
        return new BatwingBrumeEffect(this);
    }
}