package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.watchers.common.ManaSpentToCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BergStrider extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifact or creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public BergStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Berg Strider enters the battlefield, tap target artifact or creature an opponent controls. If {S} was spent to cast this spell, that permanent doesn't untap during its controller's next untap step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new BergStriderEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability, new ManaSpentToCastWatcher());
    }

    private BergStrider(final BergStrider card) {
        super(card);
    }

    @Override
    public BergStrider copy() {
        return new BergStrider(this);
    }
}

class BergStriderEffect extends OneShotEffect {

    BergStriderEffect() {
        super(Outcome.Benefit);
        staticText = "If {S} was spent to cast this spell, " +
                "that permanent doesn't untap during its controller's next untap step.";
    }

    private BergStriderEffect(final BergStriderEffect effect) {
        super(effect);
    }

    @Override
    public BergStriderEffect copy() {
        return new BergStriderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(
                ManaSpentToCastWatcher.class, source.getSourceId()
        );
        if (watcher == null) {
            return false;
        }
        Mana payment = watcher.getAndResetLastPayment();
        if (payment == null || payment.getSnow() < 1) {
            return false;
        }
        game.addEffect(new DontUntapInControllersNextUntapStepTargetEffect(), source);
        return true;
    }
}
