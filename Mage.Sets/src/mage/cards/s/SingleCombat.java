package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SingleCombat extends CardImpl {

    public SingleCombat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Each player chooses a creature or planeswalker they control, then sacrifices the rest. Players can't cast creature or planeswalker spells until the end of your next turn.
        this.getSpellAbility().addEffect(new SingleCombatEffect());
        this.getSpellAbility().addEffect(new SingleCombatRestrictionEffect());
    }

    private SingleCombat(final SingleCombat card) {
        super(card);
    }

    @Override
    public SingleCombat copy() {
        return new SingleCombat(this);
    }
}

class SingleCombatEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("creature or planeswalker you control (to keep)");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    SingleCombatEffect() {
        super(Outcome.Benefit);
        staticText = "Each player chooses a creature or planeswalker they control, then sacrifices the rest";
    }

    private SingleCombatEffect(final SingleCombatEffect effect) {
        super(effect);
    }

    @Override
    public SingleCombatEffect copy() {
        return new SingleCombatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filterSac = new FilterCreatureOrPlaneswalkerPermanent();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Target target = new TargetPermanent(filter);
            target.setNotTarget(true);
            if (player.choose(outcome, target, source, game)) {
                filterSac.add(Predicates.not(new PermanentIdPredicate(target.getFirstTarget())));
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterSac, source.getControllerId(), game)) {
            permanent.sacrifice(source, game);
        }
        return true;
    }
}

class SingleCombatRestrictionEffect extends ContinuousRuleModifyingEffectImpl {

    SingleCombatRestrictionEffect() {
        super(Duration.UntilEndOfYourNextTurn, Outcome.Neutral);
        staticText = "Players can't cast creature or planeswalker spells until the end of your next turn";
    }

    private SingleCombatRestrictionEffect(final SingleCombatRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public SingleCombatRestrictionEffect copy() {
        return new SingleCombatRestrictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        return card != null && (card.isCreature(game) || card.isPlaneswalker(game));
    }
}
