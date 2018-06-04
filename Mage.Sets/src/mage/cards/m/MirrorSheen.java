
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class MirrorSheen extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell that targets you");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
        filter.add(new TargetYouPredicate());
    }

    public MirrorSheen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U/R}{U/R}");

        // {1}{UR}{UR}: Copy target instant or sorcery spell that targets you. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), new ManaCostsImpl("{1}{U/R}{U/R}"));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);

    }

    public MirrorSheen(final MirrorSheen card) {
        super(card);
    }

    @Override
    public MirrorSheen copy() {
        return new MirrorSheen(this);
    }
}

class TargetYouPredicate implements ObjectPlayerPredicate<ObjectPlayer<StackObject>> {

    @Override
    public boolean apply(ObjectPlayer<StackObject> input, Game game) {
        UUID controllerId = input.getPlayerId();
        if (controllerId == null) {
            return false;
        }

        for (Target target : input.getObject().getStackAbility().getTargets()) {
            for (UUID targetId : target.getTargets()) {
                if (controllerId.equals(targetId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "spell that targets you";
    }
}
