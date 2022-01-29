package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author jonubuu
 */
public final class CrypticCommand extends CardImpl {

    public CrypticCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}{U}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Counter target spell;
        Effect effect1 = new CounterTargetEffect();
        effect1.setText("Counter target spell.");
        this.getSpellAbility().addEffect(effect1);
        this.getSpellAbility().addTarget(new TargetSpell());
        // or return target permanent to its owner's hand;
        Mode mode = new Mode();
        Effect effect2 = new ReturnToHandTargetEffect();
        effect2.setText("Return target permanent to its owner's hand.");
        mode.addEffect(effect2);
        mode.addTarget(new TargetPermanent());
        this.getSpellAbility().getModes().addMode(mode);
        // or tap all creatures your opponents control;
        mode = new Mode();
        mode.addEffect(new CrypticCommandEffect());
        this.getSpellAbility().getModes().addMode(mode);
        // or draw a card.
        mode = new Mode();
        Effect effect3 = new DrawCardSourceControllerEffect(1);
        mode.addEffect(effect3);
        effect3.setText("Draw a card.");
        this.getSpellAbility().getModes().addMode(mode);
    }

    private CrypticCommand(final CrypticCommand card) {
        super(card);
    }

    @Override
    public CrypticCommand copy() {
        return new CrypticCommand(this);
    }
}

class CrypticCommandEffect extends OneShotEffect {

    public CrypticCommandEffect() {
        super(Outcome.Tap);
        staticText = "Tap all creatures your opponents control";
    }

    public CrypticCommandEffect(final CrypticCommandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, player.getId(), source.getSourceId(), game)) {
            creature.tap(source, game);
        }
        return true;
    }

    @Override
    public CrypticCommandEffect copy() {
        return new CrypticCommandEffect(this);
    }
}
