package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class SyggsCommand extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MERFOLK);

    public SyggsCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.SORCERY}, "{1}{W}{U}");

        this.subtype.add(SubType.MERFOLK);

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Create a token that's a copy of target Merfolk you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // * Creatures target player controls gain lifelink until end of turn.
        this.getSpellAbility().addMode(new Mode(new SyggsCommandGainLifelinkEffect()).addTarget(new TargetPlayer()));

        // * Target player draws a card.
        this.getSpellAbility().addMode(new Mode(new DrawCardTargetEffect(1)).addTarget(new TargetPlayer()));

        // * Tap target creature. Put a stun counter on it.
        Mode mode = new Mode(new TapTargetEffect());
        mode.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).withTargetDescription("it"));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private SyggsCommand(final SyggsCommand card) {
        super(card);
    }

    @Override
    public SyggsCommand copy() {
        return new SyggsCommand(this);
    }
}

class SyggsCommandGainLifelinkEffect extends OneShotEffect {

    SyggsCommandGainLifelinkEffect() {
        super(Outcome.AddAbility);
        staticText = "Creatures target player controls gain lifelink until end of turn";
    }

    private SyggsCommandGainLifelinkEffect(final SyggsCommandGainLifelinkEffect effect) {
        super(effect);
    }

    @Override
    public SyggsCommandGainLifelinkEffect copy() {
        return new SyggsCommandGainLifelinkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate((player.getId())));
            ContinuousEffect effect = new GainAbilityAllEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, filter);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
