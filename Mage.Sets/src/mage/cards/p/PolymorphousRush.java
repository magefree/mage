
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public final class PolymorphousRush extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public PolymorphousRush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Strive - Polymorphous Rush costs {1}{U} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{1}{U}"));

        // Choose a creature on the battlefield. Any number of target creatures you control each become a copy of that creature until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, false));
        this.getSpellAbility().addEffect(new PolymorphousRushCopyEffect());

    }

    public PolymorphousRush(final PolymorphousRush card) {
        super(card);
    }

    @Override
    public PolymorphousRush copy() {
        return new PolymorphousRush(this);
    }
}

class PolymorphousRushCopyEffect extends OneShotEffect {

    public PolymorphousRushCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "Choose a creature on the battlefield. Any number of target creatures you control each become a copy of that creature until end of turn";
    }

    public PolymorphousRushCopyEffect(final PolymorphousRushCopyEffect effect) {
        super(effect);
    }

    @Override
    public PolymorphousRushCopyEffect copy() {
        return new PolymorphousRushCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCreaturePermanent(new FilterCreaturePermanent(""));
            target.setNotTarget(true);
            target.setTargetName("a creature on the battlefield (creature to copy)");
            if (target.canChoose(source.getId(), controller.getId(), game) && controller.chooseTarget(outcome, target, source, game)) {
                Permanent copyFromCreature = game.getPermanent(target.getFirstTarget());
                if (copyFromCreature != null) {
                    for (UUID copyToId : getTargetPointer().getTargets(game, source)) {
                        Permanent copyToCreature = game.getPermanent(copyToId);
                        if (copyToCreature != null) {
                            game.copyPermanent(Duration.EndOfTurn, copyFromCreature, copyToId, source, new EmptyApplyToPermanent());
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
