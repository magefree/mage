package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Trickbind extends CardImpl {

    public Trickbind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");


        // Split second
        this.addAbility(new SplitSecondAbility());

        // Counter target activated or triggered ability. If a permanent's ability is countered this way, activated abilities of that permanent can't be activated this turn.
        this.getSpellAbility().addEffect(new TrickbindCounterEffect());
        this.getSpellAbility().addTarget(new TargetActivatedOrTriggeredAbility());
    }

    private Trickbind(final Trickbind card) {
        super(card);
    }

    @Override
    public Trickbind copy() {
        return new Trickbind(this);
    }
}

class TrickbindCounterEffect extends OneShotEffect {

    public TrickbindCounterEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target activated or triggered ability. If a permanent's ability is countered this way, activated abilities of that permanent can't be activated this turn";
    }

    private TrickbindCounterEffect(final TrickbindCounterEffect effect) {
        super(effect);
    }

    @Override
    public TrickbindCounterEffect copy() {
        return new TrickbindCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        if (stackObject != null && game.getStack().counter(source.getFirstTarget(), source, game)) {
            TrickbindCantActivateEffect effect = new TrickbindCantActivateEffect();
            effect.setTargetPointer(new FixedTarget(stackObject.getSourceId()));
            game.getContinuousEffects().addEffect(effect, source);
            return true;
        }
        return false;
    }

}

class TrickbindCantActivateEffect extends RestrictionEffect {

    public TrickbindCantActivateEffect() {
        super(Duration.EndOfTurn);
        staticText = "Activated abilities of that permanent can't be activated this turn";
    }

    private TrickbindCantActivateEffect(final TrickbindCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return getTargetPointer().getFirst(game, source).equals(permanent.getId());
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public TrickbindCantActivateEffect copy() {
        return new TrickbindCantActivateEffect(this);
    }

}