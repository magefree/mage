package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
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

/**
 *
 * @author noahg
 */
public final class DeliriumCard extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerIsActivePlayerPredicate());
    }

    public DeliriumCard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{R}");
        

        // Cast this spell only during an opponent’s turn.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(OnOpponentsTurnCondition.instance, "Cast this spell only during an opponent’s turn."));
        // Tap target creature that player controls. That creature deals damage equal to its power to the player. Prevent all combat damage that would be dealt to and dealt by the creature this turn.
        this.spellAbility.addEffect(new TapTargetEffect().setText("tap target creature that player controls"));
        this.spellAbility.addTarget(new TargetCreaturePermanent(filter));
        this.spellAbility.addEffect(new DeliriumCardEffect());
        this.spellAbility.addEffect(new PreventCombatDamageBySourceEffect(Duration.EndOfTurn).setText("Prevent all combat damage that would be dealt to"));
        this.spellAbility.addEffect(new PreventCombatDamageToSourceEffect(Duration.EndOfTurn).setText("and dealt by the creature this turn."));
    }

    public DeliriumCard(final DeliriumCard card) {
        super(card);
    }

    @Override
    public DeliriumCard copy() {
        return new DeliriumCard(this);
    }
}

class DeliriumCardEffect extends OneShotEffect {

    public DeliriumCardEffect() {
        super(Outcome.Damage);
        this.staticText = "that creature deals damage equal to its power to the player";
    }

    public DeliriumCardEffect(DeliriumCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            int amount = creature.getPower().getValue();
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                controller.damage(amount, creature.getId(), game, false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public DeliriumCardEffect copy() {
        return new DeliriumCardEffect(this);
    }
}