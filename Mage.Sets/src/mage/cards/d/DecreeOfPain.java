
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DecreeOfPain extends CardImpl {

    public DecreeOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}{B}");

        // Destroy all creatures. They can't be regenerated. Draw a card for each creature destroyed this way.
        this.getSpellAbility().addEffect(new DecreeOfPainEffect());
        // Cycling {3}{B}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}{B}{B}")));
        // When you cycle Decree of Pain, all creatures get -2/-2 until end of turn.
        Ability ability = new CycleTriggeredAbility(new BoostAllEffect(-2, -2, Duration.EndOfTurn));
        this.addAbility(ability);
    }

    private DecreeOfPain(final DecreeOfPain card) {
        super(card);
    }

    @Override
    public DecreeOfPain copy() {
        return new DecreeOfPain(this);
    }
}

class DecreeOfPainEffect extends OneShotEffect {

    public DecreeOfPainEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures. They can't be regenerated. Draw a card for each creature destroyed this way";
    }

    public DecreeOfPainEffect(final DecreeOfPainEffect effect) {
        super(effect);
    }

    @Override
    public DecreeOfPainEffect copy() {
        return new DecreeOfPainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int destroyedCreature = 0;
            for (Permanent creature : game.getState().getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game)) {
                if (creature.destroy(source, game, true)) {
                    destroyedCreature++;
                }
            }
            if (destroyedCreature > 0) {
                controller.drawCards(destroyedCreature, source, game);
            }
            return true;
        }
        return false;
    }
}
