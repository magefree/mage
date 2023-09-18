package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

/**
 * @author JRHerlehy
 * Created on 4/4/18.
 */
public final class SealAway extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(TappedPredicate.TAPPED);
    }

    public SealAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        //Flash
        this.addAbility(FlashAbility.getInstance());

        //When Seal Away enters the battlefield, exile target tapped creature an opponent controls until Seal Away leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SealAway(final SealAway card) {
        super(card);
    }

    @Override
    public SealAway copy() {
        return new SealAway(this);
    }
}
