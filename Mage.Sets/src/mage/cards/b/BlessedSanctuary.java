package mage.cards.b;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PreventAllNonCombatDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.UnicornToken;

import java.util.UUID;

public class BlessedSanctuary extends CardImpl {

    private static final FilterControlledCreaturePermanent filterNontoken = new FilterControlledCreaturePermanent("a nontoken creature");

    static {
        filterNontoken.add(TokenPredicate.FALSE);
    }

    public BlessedSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        //Prevent all noncombat damage that would be dealt to you and creatures you control.
        this.addAbility(new SimpleStaticAbility(new PreventAllNonCombatDamageToAllEffect(
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, true)));

        //Whenever a nontoken creature enters the battlefield under your control, create a 2/2 white Unicorn creature token.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new UnicornToken()), filterNontoken, false));
    }

    private BlessedSanctuary(final BlessedSanctuary card) {
        super(card);
    }

    @Override
    public BlessedSanctuary copy() {
        return new BlessedSanctuary(this);
    }
}
