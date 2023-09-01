
package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class RodOfSpanking extends CardImpl {

    public RodOfSpanking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT }, "{1}");

        // 2, T: Rod of Spanking deals 1 damage to target player. Then untap Rod of
        // Spanking unless that player says "Thank you, sir. May I have another?"
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RodOfSpankingEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private RodOfSpanking(final RodOfSpanking card) {
        super(card);
    }

    @Override
    public RodOfSpanking copy() {
        return new RodOfSpanking(this);
    }
}

class RodOfSpankingEffect extends OneShotEffect {

    public RodOfSpankingEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 1 damage to target player. Then untap {this} unless that player says \"Thank you, sir. May I have another?\"";
    }

    private RodOfSpankingEffect(final RodOfSpankingEffect effect) {
        super(effect);
    }

    @Override
    public RodOfSpankingEffect copy() {
        return new RodOfSpankingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player target = game.getPlayer(source.getFirstTarget());
        new DamageTargetEffect(1).apply(game, source);
        if (target != null) {
            if (target.chooseUse(Outcome.Untap, "Say \"Thank you, sir. May I have another?\"", source, game)) {
                game.informPlayers(target.getLogName() + ": Thank you, sir. May I have another?");
            } else {
                new UntapSourceEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }

}
