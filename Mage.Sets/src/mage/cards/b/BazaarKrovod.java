
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author jeffwadsworth
 */
public final class BazaarKrovod extends CardImpl {

    static final FilterAttackingCreature filter = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BazaarKrovod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever Bazaar Krovod attacks, another target attacking creature gets +0/+2 until end of turn. Untap that creature.
        Ability ability = new AttacksTriggeredAbility(new BazaarKrovodEffect(), false);
        ability.addTarget(new TargetAttackingCreature(1, 1, filter, false));
        this.addAbility(ability);
    }

    private BazaarKrovod(final BazaarKrovod card) {
        super(card);
    }

    @Override
    public BazaarKrovod copy() {
        return new BazaarKrovod(this);
    }
}

class BazaarKrovodEffect extends OneShotEffect {

    public BazaarKrovodEffect() {
        super(Outcome.Benefit);
        staticText = "another target attacking creature gets +0/+2 until end of turn. Untap that creature";
    }

    public BazaarKrovodEffect(BazaarKrovodEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            game.addEffect(new BoostTargetEffect(0, 2, Duration.EndOfTurn), source);
            permanent.untap(game);
            return true;
        }
        return false;
    }

    @Override
    public BazaarKrovodEffect copy() {
        return new BazaarKrovodEffect(this);
    }

}
