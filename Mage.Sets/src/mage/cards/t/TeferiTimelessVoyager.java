package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeferiTimelessVoyager extends CardImpl {

    public TeferiTimelessVoyager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);
        this.setStartingLoyalty(4);

        // +1: Draw a card.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1));

        // −3: Put target creature on top of its owner's library.
        Ability ability = new LoyaltyAbility(new PutOnLibraryTargetEffect(true), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // −8: Each creature target opponent controls phases out. Until the end of your next turn, they can't phase in.
        ability = new LoyaltyAbility(new TeferiTimelessVoyagerEffect(), -8);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TeferiTimelessVoyager(final TeferiTimelessVoyager card) {
        super(card);
    }

    @Override
    public TeferiTimelessVoyager copy() {
        return new TeferiTimelessVoyager(this);
    }
}

class TeferiTimelessVoyagerEffect extends OneShotEffect {

    TeferiTimelessVoyagerEffect() {
        super(Outcome.Benefit);
        staticText = "each creature target opponent controls phases out. " +
                "Until the end of your next turn, they can't phase in";
    }

    private TeferiTimelessVoyagerEffect(final TeferiTimelessVoyagerEffect effect) {
        super(effect);
    }

    @Override
    public TeferiTimelessVoyagerEffect copy() {
        return new TeferiTimelessVoyagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game
                .getBattlefield()
                .getAllActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE, source.getFirstTarget(), game
                )) {
            MageObjectReference mor = new MageObjectReference(permanent, game);
            permanent.phaseOut(game);
            game.addEffect(new TeferiTimelessVoyagerPhaseEffect(mor), source);
        }
        return true;
    }
}

class TeferiTimelessVoyagerPhaseEffect extends ContinuousRuleModifyingEffectImpl {

    private int castOnTurn = 0;
    private final MageObjectReference mor;

    TeferiTimelessVoyagerPhaseEffect(MageObjectReference mor) {
        super(Duration.Custom, Outcome.Neutral);
        this.mor = mor;
    }

    private TeferiTimelessVoyagerPhaseEffect(final TeferiTimelessVoyagerPhaseEffect effect) {
        super(effect);
        this.castOnTurn = effect.castOnTurn;
        this.mor = effect.mor;
    }

    @Override
    public TeferiTimelessVoyagerPhaseEffect copy() {
        return new TeferiTimelessVoyagerPhaseEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        castOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (castOnTurn != game.getTurnNum() && game.getPhase().getStep().getType() == PhaseStep.END_TURN) {
            return game.isActivePlayer(source.getControllerId());
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_IN;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return this.mor.refersTo(event.getTargetId(), game);
    }
}
