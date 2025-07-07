package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Duneblast extends CardImpl {

    public Duneblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{B}{G}");

        // Choose up to one creature. Destroy the rest.
        this.getSpellAbility().addEffect(new DuneblastEffect());
    }

    private Duneblast(final Duneblast card) {
        super(card);
    }

    @Override
    public Duneblast copy() {
        return new Duneblast(this);
    }
}

class DuneblastEffect extends OneShotEffect {

    DuneblastEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Choose up to one creature. Destroy the rest";
    }

    private DuneblastEffect(final DuneblastEffect effect) {
        super(effect);
    }

    @Override
    public DuneblastEffect copy() {
        return new DuneblastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetCreaturePermanent(0, 1);
        target.withNotTarget(true);
        target.withChooseHint("to keep");
        target.setRequired(true);
        controller.choose(outcome, target, source, game);
        Permanent creatureToKeep = game.getPermanent(target.getFirstTarget());
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), source, game
        )) {
            if (!creature.equals(creatureToKeep)) {
                creature.destroy(source, game, false);
            }
        }
        return true;
    }
}
