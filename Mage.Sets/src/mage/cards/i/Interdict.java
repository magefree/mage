package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.common.TargetActivatedAbility;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Interdict extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("activated ability from an artifact, creature, enchantment, or land");

    static {
        filter.add(new InterdictPredicate());
    }

    public Interdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target activated ability from an artifact, creature, enchantment, or land. That permanent's activated abilities can't be activated this turn.
        this.getSpellAbility().addEffect(new InterdictCounterEffect());
        this.getSpellAbility().addTarget(new TargetActivatedAbility(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Interdict(final Interdict card) {
        super(card);
    }

    @Override
    public Interdict copy() {
        return new Interdict(this);
    }
}

class InterdictPredicate implements Predicate<StackObject> {

    public InterdictPredicate() {
    }

    @Override
    public boolean apply(StackObject input, Game game) {
        if (input instanceof StackAbility && ((StackAbility) input).getAbilityType() == AbilityType.ACTIVATED) {
            MageObject sourceObject = ((StackAbility) input).getSourceObject(game);
            if (sourceObject != null) {
                return (sourceObject.isArtifact(game)
                        || sourceObject.isEnchantment(game)
                        || sourceObject.isCreature(game)
                        || sourceObject.isLand(game));
            }
        }
        return false;
    }
}

class InterdictCounterEffect extends OneShotEffect {

    public InterdictCounterEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target activated ability from an artifact, creature, enchantment, or land. That permanent's activated abilities can't be activated this turn.";
    }

    public InterdictCounterEffect(final InterdictCounterEffect effect) {
        super(effect);
    }

    @Override
    public InterdictCounterEffect copy() {
        return new InterdictCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        if (stackObject != null && game.getStack().counter(source.getFirstTarget(), source, game)) {
            Permanent sourcePermanent = stackObject.getStackAbility().getSourcePermanentIfItStillExists(game);
            if (sourcePermanent != null) {
                InterdictCantActivateEffect effect = new InterdictCantActivateEffect();
                effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                game.getContinuousEffects().addEffect(effect, source);
            }
            return true;
        }
        return false;
    }

}

class InterdictCantActivateEffect extends RestrictionEffect {

    public InterdictCantActivateEffect() {
        super(Duration.EndOfTurn);
        staticText = "That permanent's activated abilities can't be activated this turn";
    }

    public InterdictCantActivateEffect(final InterdictCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public InterdictCantActivateEffect copy() {
        return new InterdictCantActivateEffect(this);
    }

}
