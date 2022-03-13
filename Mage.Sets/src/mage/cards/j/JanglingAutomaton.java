package mage.cards.j;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author arcox
 */
public final class JanglingAutomaton extends CardImpl {
    public JanglingAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Jangling Automaton attacks, untap all creatures defending player controls.
        this.addAbility(new AttacksTriggeredAbility(new JanglingAutomatonEffect(), false));
    }

    private JanglingAutomaton(final JanglingAutomaton card) {
        super(card);
    }

    @Override
    public JanglingAutomaton copy() {
        return new JanglingAutomaton(this);
    }
}

class JanglingAutomatonEffect extends OneShotEffect {
    public JanglingAutomatonEffect() {
        super(Outcome.Untap);
        this.staticText = "untap all creatures defending player controls";
    }

    public JanglingAutomatonEffect(final JanglingAutomatonEffect effect) {
        super(effect);
    }

    @Override
    public JanglingAutomatonEffect copy() {
        return new JanglingAutomatonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defenderId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
        if (defenderId != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(defenderId));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                permanent.untap(game);
            }
            return true;
        }
        return false;
    }
}
