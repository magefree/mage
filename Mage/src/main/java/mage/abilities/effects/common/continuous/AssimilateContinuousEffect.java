package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * 701.72. Assimilate
 * <p>
 * 701.72a To assimilate a permanent, gain control of it and put a +1/+1 counter on it. It becomes
 * a Borg artifact creature.
 * <p>
 * 701.72b To assimilate a permanent card that’s in a zone other than the battlefield, put it onto
 * the battlefield under your control with a +1/+1 counter on it. It’s a Borg artifact creature.
 * <p>
 * 701.72c An object may have an ability printed on it that assimilates another object and another
 * ability that refers to permanents “assimilated with [this object].” These abilities are linked;
 * the second refers only to permanents that have been assimilated due to the first. See rule 607,
 * “Linked Abilities.”
 * <p>
 * 205.1b Some effects change an object’s card type, supertype, or subtype but specify that the
 * object retains a prior card type, supertype, or subtype. In such cases, all the object’s prior
 * card types, supertypes, and subtypes are retained. This rule applies to effects that use phrases
 * such as “in addition to its other types” or that state that something is “still a [type,
 * supertype, or subtype].” Some effects state that an object becomes an “artifact creature”; these
 * effects also allow the object to retain all of its prior card types and subtypes. Some effects
 * state that an object becomes a “[creature type or types] artifact creature”; these effects also
 * allow the object to retain all of its prior card types and subtypes other than creature types,
 * but replace any existing creature types.
 *
 * @author muz, JayDi85
 */
public class AssimilateContinuousEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;

    public AssimilateContinuousEffect(MageObjectReference mor) {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        this.staticText = "It's a Borg artifact creature and loses all other creature types.";
        this.mor = mor;
        this.dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    private AssimilateContinuousEffect(final AssimilateContinuousEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public AssimilateContinuousEffect copy() {
        return new AssimilateContinuousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(mor);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent == null) {
            this.discard();
            return false;
        }
        permanent.addCardType(game, CardType.ARTIFACT);
        permanent.addCardType(game, CardType.CREATURE);
        permanent.removeAllCreatureTypes(game);
        permanent.addSubType(game, SubType.BORG);
        return true;
    }
}
