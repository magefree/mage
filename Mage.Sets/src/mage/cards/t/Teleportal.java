package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Teleportal extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public Teleportal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{R}");

        // Target creature you control gets +1/+0 until end of turn and can't be blocked this turn.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect());

        // Overload {3}{U}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility ability = new OverloadAbility(this, new BoostAllEffect(1, 0, Duration.EndOfTurn, filter, false), new ManaCostsImpl<>("{3}{U}{R}"));
        ability.addEffect(new TeleportalEffect(filter));
        this.addAbility(ability);
    }

    private Teleportal(final Teleportal card) {
        super(card);
    }

    @Override
    public Teleportal copy() {
        return new Teleportal(this);
    }
}

class TeleportalEffect extends OneShotEffect {

    private FilterCreaturePermanent filter;

    public TeleportalEffect(FilterCreaturePermanent filter) {
        super(Outcome.ReturnToHand);
        staticText = "each creature you control can't be blocked this turn";
        this.filter = filter;
    }

    public TeleportalEffect(final TeleportalEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            CantBeBlockedTargetEffect effect = new CantBeBlockedTargetEffect();
            effect.setTargetPointer(new FixedTarget(creature.getId(), game));
            game.addEffect(effect, source);
        }
        return true;
    }

    @Override
    public TeleportalEffect copy() {
        return new TeleportalEffect(this);
    }

}
