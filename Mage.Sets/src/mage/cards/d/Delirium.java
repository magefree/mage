package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIsActivePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author noahg
 */
public final class Delirium extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerIsActivePlayerPredicate());
    }

    public Delirium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{R}");


        // Cast this spell only during an opponent’s turn.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(OnOpponentsTurnCondition.instance, "Cast this spell only during an opponent's turn."));
        // Tap target creature that player controls. That creature deals damage equal to its power to the player. Prevent all combat damage that would be dealt to and dealt by the creature this turn.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap target creature that player controls"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DeliriumEffect());
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, true).setText("Prevent all combat damage that would be dealt to"));
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn, true).setText("and dealt by the creature this turn."));
    }

    private Delirium(final Delirium card) {
        super(card);
    }

    @Override
    public Delirium copy() {
        return new Delirium(this);
    }
}

class DeliriumEffect extends OneShotEffect {

    public DeliriumEffect() {
        super(Outcome.Damage);
        this.staticText = "that creature deals damage equal to its power to the player";
    }

    private DeliriumEffect(final DeliriumEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            int amount = creature.getPower().getValue();
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                controller.damage(amount, creature.getId(), source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public DeliriumEffect copy() {
        return new DeliriumEffect(this);
    }
}