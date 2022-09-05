package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrisonRealm extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public PrisonRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Prison Realm enters the battlefield, exile target creature or planeswalker an opponent controls until Prison Realm leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

        // When Prison Realm enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));
    }

    private PrisonRealm(final PrisonRealm card) {
        super(card);
    }

    @Override
    public PrisonRealm copy() {
        return new PrisonRealm(this);
    }
}
