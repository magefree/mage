package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.YouDontLoseManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheLastAgniKai extends CardImpl {

    public TheLastAgniKai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature you control fights target creature an opponent controls. If the creature the opponent controls is dealt excess damage this way, add that much {R}.
        this.getSpellAbility().addEffect(new TheLastAgniKaiEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // Until end of turn, you don't lose unspent red mana as steps and phases end.
        this.getSpellAbility().addEffect(new YouDontLoseManaEffect(Duration.EndOfTurn, ManaType.RED).concatBy("<br>"));
    }

    private TheLastAgniKai(final TheLastAgniKai card) {
        super(card);
    }

    @Override
    public TheLastAgniKai copy() {
        return new TheLastAgniKai(this);
    }
}

class TheLastAgniKaiEffect extends OneShotEffect {

    TheLastAgniKaiEffect() {
        super(Outcome.Benefit);
        staticText = "target creature you control fights target creature an opponent controls. " +
                "If the creature the opponent controls is dealt excess damage this way, add that much {R}";
        this.setTargetPointer(new EachTargetPointer());
    }

    private TheLastAgniKaiEffect(final TheLastAgniKaiEffect effect) {
        super(effect);
    }

    @Override
    public TheLastAgniKaiEffect copy() {
        return new TheLastAgniKaiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.size() < 2) {
            return false;
        }
        int excess = permanents.get(0).fightWithExcess(permanents.get(1), source, game, true);
        if (excess > 0) {
            Optional.ofNullable(source)
                    .map(Controllable::getControllerId)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.getManaPool().addMana(Mana.RedMana(excess), game, source));
        }
        return true;
    }
}
