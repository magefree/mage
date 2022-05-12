package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.KickerWithAnyNumberModesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InscriptionOfAbundance extends CardImpl {

    public InscriptionOfAbundance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Kicker {2}{G}
        this.addAbility(new KickerWithAnyNumberModesAbility("{2}{G}"));

        // Choose one. If this spell was kicked, choose any number instead.
        // • Put two +1/+1 counters on target creature.
        this.getSpellAbility().getModes().setChooseText("choose one. " +
                "If this spell was kicked, choose any number instead.");
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // • Target player gains X life, where X is the greatest power among creatures they control.
        Mode mode = new Mode(new InscriptionOfAbundanceEffect());
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);

        // • Target creature you control fights target creature you don't control.
        mode = new Mode(new FightTargetsEffect(false));
        mode.addTarget(new TargetControlledCreaturePermanent());
        mode.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addMode(mode);
    }

    private InscriptionOfAbundance(final InscriptionOfAbundance card) {
        super(card);
    }

    @Override
    public InscriptionOfAbundance copy() {
        return new InscriptionOfAbundance(this);
    }
}

class InscriptionOfAbundanceEffect extends OneShotEffect {

    InscriptionOfAbundanceEffect() {
        super(Outcome.Benefit);
        staticText = "target player gains X life, where X is the greatest power among creatures they control";
    }

    private InscriptionOfAbundanceEffect(final InscriptionOfAbundanceEffect effect) {
        super(effect);
    }

    @Override
    public InscriptionOfAbundanceEffect copy() {
        return new InscriptionOfAbundanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        int maxPower = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getFirstTarget(), source, game
                ).stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
        if (maxPower > 0) {
            player.gainLife(maxPower, game, source);
            return true;
        }
        return false;
    }
}
