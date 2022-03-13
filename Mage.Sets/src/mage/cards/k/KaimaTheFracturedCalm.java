package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KaimaTheFracturedCalm extends CardImpl {

    public KaimaTheFracturedCalm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, goad each creature your opponents control that's enchanted by an Aura you control. Put a +1/+1 counter on Kaima, the Fractured Calm for each creature goaded this way.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new KaimaTheFracturedCalmEffect(), TargetController.YOU, false
        ));
    }

    private KaimaTheFracturedCalm(final KaimaTheFracturedCalm card) {
        super(card);
    }

    @Override
    public KaimaTheFracturedCalm copy() {
        return new KaimaTheFracturedCalm(this);
    }
}

class KaimaTheFracturedCalmEffect extends OneShotEffect {

    KaimaTheFracturedCalmEffect() {
        super(Outcome.Benefit);
        staticText = "goad each creature your opponents control that's enchanted by an Aura you control. " +
                "Put a +1/+1 counter on {this} for each creature goaded this way";
    }

    private KaimaTheFracturedCalmEffect(final KaimaTheFracturedCalmEffect effect) {
        super(effect);
    }

    @Override
    public KaimaTheFracturedCalmEffect copy() {
        return new KaimaTheFracturedCalmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int goaded = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (permanent
                    .getAttachments()
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .noneMatch(p -> p.isControlledBy(source.getControllerId())
                            && p.hasSubtype(SubType.AURA, game))) {
                continue;
            }
            game.addEffect(new GoadTargetEffect().setTargetPointer(new FixedTarget(permanent, game)), source);
            goaded++;
        }
        if (goaded < 1) {
            return false;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(goaded), source, game);
        }
        return true;
    }
}
