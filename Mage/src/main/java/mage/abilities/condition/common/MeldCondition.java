package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author emerald000
 */
public class MeldCondition implements Condition {

    private final String message;
    private final FilterPermanent filter;
    private final boolean attacking;

    public MeldCondition(String meldWithName) {
        this(meldWithName, CardType.CREATURE);
    }

    public MeldCondition(String meldWithName, CardType cardType) {
        this(meldWithName, cardType, false);
    }

    public MeldCondition(String meldWithName, CardType cardType, boolean attacking) {
        this.message = "you both own and control {this} and "
                + CardUtil.addArticle(cardType.toString().toLowerCase())
                + " named " + meldWithName;
        this.filter = new FilterControlledPermanent();
        this.filter.add(TargetController.YOU.getOwnerPredicate());
        this.filter.add(cardType.getPredicate());
        this.filter.add(new NamePredicate(meldWithName));
        if (attacking) {
            this.filter.add(AttackingPredicate.instance);
        }
        this.attacking = attacking;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        return sourcePermanent != null
                && sourcePermanent.isControlledBy(source.getControllerId())
                && sourcePermanent.isOwnedBy(source.getControllerId())
                && (!attacking || sourcePermanent.isAttacking())
                && game.getBattlefield().contains(filter, source, game, 1);
    }

    @Override
    public String toString() {
        return message;
    }
}
