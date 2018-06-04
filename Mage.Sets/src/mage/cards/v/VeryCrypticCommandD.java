
package mage.cards.v;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NumberOfTargetsPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.TargetStackObject;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class VeryCrypticCommandD extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("spell or ability with a single target");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(new NumberOfTargetsPredicate(1));
        filter2.add(Predicates.not(new TokenPredicate()));
    }

    public VeryCrypticCommandD(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}{U}");

        // Choose two - 
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Return target permanent to its controller�s hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());

        // Draw two cards, then discard a card.
        Mode mode = new Mode();
        mode.getEffects().add(new DrawCardSourceControllerEffect(2));
        Effect effect = new DiscardControllerEffect(1);
        effect.setText(", then discard a card");
        mode.getEffects().add(effect);
        this.getSpellAbility().getModes().addMode(mode);

        // Change the target of target spell with a single target.
        mode = new Mode();
        mode.getEffects().add(new ChooseNewTargetsTargetEffect(true, true));
        mode.getTargets().add(new TargetStackObject(filter));
        this.getSpellAbility().getModes().addMode(mode);

        // Turn over target nontoken creature.
        mode = new Mode();
        mode.getEffects().add(new TurnOverEffect());
        mode.getTargets().add(new TargetCreaturePermanent(filter2));
        this.getSpellAbility().getModes().addMode(mode);
    }

    public VeryCrypticCommandD(final VeryCrypticCommandD card) {
        super(card);
    }

    @Override
    public VeryCrypticCommandD copy() {
        return new VeryCrypticCommandD(this);
    }
}

class TurnOverEffect extends OneShotEffect {

    TurnOverEffect() {
        super(Outcome.Benefit);
        this.staticText = "Turn over target nontoken creature";
    }

    TurnOverEffect(final TurnOverEffect effect) {
        super(effect);
    }

    @Override
    public TurnOverEffect copy() {
        return new TurnOverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            if (creature.isFaceDown(game)) {
                creature.turnFaceUp(game, source.getControllerId());
            } else {
                creature.turnFaceDown(game, source.getControllerId());
                MageObjectReference objectReference = new MageObjectReference(creature.getId(), creature.getZoneChangeCounter(game), game);
                game.addEffect(new BecomesFaceDownCreatureEffect(null, objectReference, Duration.Custom, FaceDownType.MANUAL), source);
            }
            return true;
        }
        return false;
    }
}
