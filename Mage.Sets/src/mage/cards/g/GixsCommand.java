package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class GixsCommand extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("each creature with power 2 or less");

    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("creature with the highest power among creatures they control");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(GreatestPowerControlledPredicate.instance);
    }

    public GixsCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Put two +1/+1 counter on up to one creature. It gains lifelink until end of turn.
        this.getSpellAbility().addEffect(new GixsCommandCounterEffect());

        // * Destroy each creature with power 2 or less.
        this.getSpellAbility().addMode(new Mode(new DestroyAllEffect(filter).setText("Destroy each creature with power 2 or less")));

        // * Return up to two creature cards from your graveyard to your hand.
        this.getSpellAbility().addMode(new Mode(new GixsCommandReturnEffect()));

        // * Each opponent sacrifices a creature with the highest power among creatures they control.
        this.getSpellAbility().addMode(new Mode(new SacrificeOpponentsEffect(filter2)));
    }

    private GixsCommand(final GixsCommand card) {
        super(card);
    }

    @Override
    public GixsCommand copy() {
        return new GixsCommand(this);
    }
}

class GixsCommandCounterEffect extends OneShotEffect {

    public GixsCommandCounterEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put two +1/+1 counter on up to one creature. It gains lifelink until end of turn.";
    }

    private GixsCommandCounterEffect(final GixsCommandCounterEffect effect) {
        super(effect);
    }

    @Override
    public GixsCommandCounterEffect copy() {
        return new GixsCommandCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCreaturePermanent target = new TargetCreaturePermanent(0, 1);
        target.setNotTarget(true);
        controller.chooseTarget(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(2), source, game);
        GainAbilityTargetEffect effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance());
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        return true;
    }
}

class GixsCommandReturnEffect extends OneShotEffect {

    public GixsCommandReturnEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return up to two creature cards from your graveyard to your hand";
    }

    private GixsCommandReturnEffect(final GixsCommandReturnEffect effect) {
        super(effect);
    }

    @Override
    public GixsCommandReturnEffect copy() {
        return new GixsCommandReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD, true);
        controller.chooseTarget(outcome, target, source, game);
        return controller.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
    }
}
