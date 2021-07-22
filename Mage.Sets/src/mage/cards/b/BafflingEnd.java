
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.DinosaurToken;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class BafflingEnd extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public BafflingEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // When Mysterious end enters the battlefield, exile target creature with converted mana cost 3 or less an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // When Mysterious end leaves the battlefield, target opponent create a 3/3 green Dinosaur creature token with trample.
        ability = new LeavesBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new DinosaurToken()), false);
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);

    }

    private BafflingEnd(final BafflingEnd card) {
        super(card);
    }

    @Override
    public BafflingEnd copy() {
        return new BafflingEnd(this);
    }
}
