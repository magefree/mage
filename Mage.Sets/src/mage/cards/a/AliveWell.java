
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.CentaurToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class AliveWell extends SplitCard {

    public AliveWell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}", "{W}", SpellAbilityType.SPLIT_FUSED);

        // Alive
        // Create a 3/3 green Centaur creature token.
        getLeftHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new CentaurToken()));

        // Well
        // You gain 2 life for each creature you control.
        getRightHalfCard().getSpellAbility().addEffect(new WellEffect());

    }

    private AliveWell(final AliveWell card) {
        super(card);
    }

    @Override
    public AliveWell copy() {
        return new AliveWell(this);
    }
}

class WellEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    public WellEffect() {
        super(Outcome.GainLife);
        staticText = "You gain 2 life for each creature you control";
    }

    public WellEffect(final WellEffect effect) {
        super(effect);
    }

    @Override
    public WellEffect copy() {
        return new WellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int life = 2 * game.getBattlefield().count(filter, source.getControllerId(), source, game);
            player.gainLife(life, game, source);
        }
        return true;
    }

}
