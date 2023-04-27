package mage.cards.k;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Construct2Token;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class KaylasCommand extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("a basic Plains card");

    static {
        filter.add(SubType.PLAINS.getPredicate());
        filter.add(SuperType.BASIC.getPredicate());
    }

    public KaylasCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Create a 2/2 colorless Construct artifact creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Construct2Token()));

        // * Put a +1/+1 counter on a creature you control. It gains double strike until end of turn.
        this.getSpellAbility().addMode(new Mode(new KaylasCommandCounterEffect()));

        // * Search your library for a basic Plains card, reveal it, put it into your hand, then shuffle.
        this.getSpellAbility().addMode(new Mode(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)));

        // * You gain 2 life and scry 2.
        Mode mode = new Mode(new GainLifeEffect(2));
        mode.addEffect(new ScryEffect(2, false).concatBy("and"));
        this.getSpellAbility().addMode(mode);
    }

    private KaylasCommand(final KaylasCommand card) {
        super(card);
    }

    @Override
    public KaylasCommand copy() {
        return new KaylasCommand(this);
    }
}

class KaylasCommandCounterEffect extends OneShotEffect {

    public KaylasCommandCounterEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put a +1/+1 counter on a creature you control. It gains double strike until end of turn.";
    }

    private KaylasCommandCounterEffect(final KaylasCommandCounterEffect effect) {
        super(effect);
    }

    @Override
    public KaylasCommandCounterEffect copy() {
        return new KaylasCommandCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        controller.chooseTarget(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        GainAbilityTargetEffect effect = new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance());
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        return true;
    }
}
