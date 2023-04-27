package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author weirddan455
 */
public final class ExtinguishTheLight extends CardImpl {

    public ExtinguishTheLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Destroy target creature or planeswalker. If its mana value was 3 or less, you gain 3 life.
        this.getSpellAbility().addEffect(new ExtinguishTheLightEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private ExtinguishTheLight(final ExtinguishTheLight card) {
        super(card);
    }

    @Override
    public ExtinguishTheLight copy() {
        return new ExtinguishTheLight(this);
    }
}

class ExtinguishTheLightEffect extends OneShotEffect {

    public ExtinguishTheLightEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature or planeswalker. If its mana value was 3 or less, you gain 3 life.";
    }

    private ExtinguishTheLightEffect(final ExtinguishTheLightEffect effect) {
        super(effect);
    }

    @Override
    public ExtinguishTheLightEffect copy() {
        return new ExtinguishTheLightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int manaValue = permanent.getManaValue();
        permanent.destroy(source, game);
        if (manaValue <= 3) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(3, game, source);
            }
        }
        return true;
    }
}
