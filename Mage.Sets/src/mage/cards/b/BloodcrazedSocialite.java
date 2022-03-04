package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BloodToken;

/**
 *
 * @author weirddan455
 */
public final class BloodcrazedSocialite extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Blood token");

    static {
        filter.add(SubType.BLOOD.getPredicate());
        filter.add(TokenPredicate.TRUE);
    }

    public BloodcrazedSocialite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Bloodcrazed Socialite enters the battlefield, create a Blood token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BloodToken())));

        // Whenever Bloodcrazed Socialite attacks, you may sacrifice a Blood token. If you do, it gets +2/+2 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn).setText("it gets +2/+2 until end of turn"),
                new SacrificeTargetCost(filter)
        )));
    }

    private BloodcrazedSocialite(final BloodcrazedSocialite card) {
        super(card);
    }

    @Override
    public BloodcrazedSocialite copy() {
        return new BloodcrazedSocialite(this);
    }
}
