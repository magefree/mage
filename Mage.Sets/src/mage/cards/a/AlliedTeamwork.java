package mage.cards.a;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlliedTeamwork extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.ALLY, "Allies");

    public AlliedTeamwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When this enchantment enters, create a 1/1 white Ally creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AllyToken())));

        // Allies you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter)));
    }

    private AlliedTeamwork(final AlliedTeamwork card) {
        super(card);
    }

    @Override
    public AlliedTeamwork copy() {
        return new AlliedTeamwork(this);
    }
}
