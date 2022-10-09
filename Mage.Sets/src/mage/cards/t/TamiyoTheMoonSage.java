
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.command.emblems.TamiyoTheMoonSageEmblem;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author North, noxx
 */
public final class TamiyoTheMoonSage extends CardImpl {

    public TamiyoTheMoonSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TAMIYO);

        this.setStartingLoyalty(4);

        // +1: Tap target permanent. It doesn't untap during its controller's next untap step.
        LoyaltyAbility ability = new LoyaltyAbility(new TapTargetEffect(), 1);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("it"));
        Target target = new TargetPermanent();
        ability.addTarget(target);
        this.addAbility(ability);

        // -2: Draw a card for each tapped creature target player controls.
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(new TappedCreaturesControlledByTargetCount()), -2);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -8: You get an emblem with "You have no maximum hand size" and "Whenever a card is put into your graveyard from anywhere, you may return it to your hand."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TamiyoTheMoonSageEmblem()), -8));
    }

    private TamiyoTheMoonSage(final TamiyoTheMoonSage card) {
        super(card);
    }

    @Override
    public TamiyoTheMoonSage copy() {
        return new TamiyoTheMoonSage(this);
    }
}

class TappedCreaturesControlledByTargetCount implements DynamicValue {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().countAll(filter, sourceAbility.getFirstTarget(), game);
    }

    @Override
    public TappedCreaturesControlledByTargetCount copy() {
        return new TappedCreaturesControlledByTargetCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "tapped creature target player controls";
    }
}

/**
 * Emblem with "You have no maximum hand size" and "Whenever a card is put into
 * your graveyard from anywhere, you may return it to your hand."
 */
