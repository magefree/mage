
package mage.cards.n;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class NemesisTrap extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("If a white creature is attacking");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(AttackingPredicate.instance);
    }

    public NemesisTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{B}{B}");
        this.subtype.add(SubType.TRAP);

        // If a white creature is attacking, you may pay {B}{B} rather than pay Nemesis Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{B}{B}"), new PermanentsOnTheBattlefieldCondition(filter, false)));

        // Exile target attacking creature. Create a token that's a copy of that creature. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new NemesisTrapEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private NemesisTrap(final NemesisTrap card) {
        super(card);
    }

    @Override
    public NemesisTrap copy() {
        return new NemesisTrap(this);
    }
}

class NemesisTrapEffect extends OneShotEffect {

    public NemesisTrapEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target attacking creature. Create a token that's a copy of that creature. Exile it at the beginning of the next end step";
    }

    public NemesisTrapEffect(final NemesisTrapEffect effect) {
        super(effect);
    }

    @Override
    public NemesisTrapEffect copy() {
        return new NemesisTrapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetedCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && targetedCreature != null) {
            // exile target
            controller.moveCards(targetedCreature, Zone.EXILED, source, game);
            // create token
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
            effect.setTargetPointer(new FixedTarget(targetedCreature, game));
            effect.apply(game, source);
            for (Permanent addedToken : effect.getAddedPermanent()) {
                Effect exileEffect = new ExileTargetEffect("Exile " + addedToken.getName() + " at the beginning of the next end step");
                exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }
}
