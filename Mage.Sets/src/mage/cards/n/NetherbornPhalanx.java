
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class NetherbornPhalanx extends CardImpl {

    public NetherbornPhalanx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Netherborn Phalanx enters the battlefield, each opponent loses 1 life for each creature they control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NetherbornPhalanxEffect());
        this.addAbility(ability);

        // Transmute {1}{B}{B}
        this.addAbility(new TransmuteAbility("{1}{B}{B}"));
    }

    private NetherbornPhalanx(final NetherbornPhalanx card) {
        super(card);
    }

    @Override
    public NetherbornPhalanx copy() {
        return new NetherbornPhalanx(this);
    }
}

class NetherbornPhalanxEffect extends OneShotEffect {

    NetherbornPhalanxEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each opponent loses 1 life for each creature they control";
    }

    private NetherbornPhalanxEffect(final NetherbornPhalanxEffect effect) {
        super(effect);
    }

    @Override
    public NetherbornPhalanxEffect copy() {
        return new NetherbornPhalanxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                final int count = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game).size();
                if (count > 0) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        opponent.loseLife(count, game, source, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
