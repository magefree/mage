package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SyphonSoul extends CardImpl {

    public SyphonSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");


        // Syphon Soul deals 2 damage to each other player. You gain life equal to the damage dealt this way.
        this.getSpellAbility().addEffect(new SyphonSoulEffect());
    }

    private SyphonSoul(final SyphonSoul card) {
        super(card);
    }

    @Override
    public SyphonSoul copy() {
        return new SyphonSoul(this);
    }
}

class SyphonSoulEffect extends OneShotEffect {
    public SyphonSoulEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to each other player. You gain life equal to the damage dealt this way";
    }

    private SyphonSoulEffect(final SyphonSoulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damageDealt = 0;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (!playerId.equals(source.getControllerId())) {
                damageDealt += game.getPlayer(playerId).damage(2, source.getSourceId(), source, game);
            }
        }
        if (damageDealt > 0) {
            game.getPlayer(source.getControllerId()).gainLife(damageDealt, game, source);
        }
        return true;
    }

    @Override
    public SyphonSoulEffect copy() {
        return new SyphonSoulEffect(this);
    }

}