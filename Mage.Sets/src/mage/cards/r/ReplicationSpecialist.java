package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReplicationSpecialist extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("a nontoken artifact");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public ReplicationSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a nontoken artifact enters the battlefield under your control, you may pay {1}{U}. If you do, create a token that's a copy of that artifact.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new DoIfCostPaid(
                        new CreateTokenCopyTargetEffect()
                                .setText("create a token that's a copy of that artifact"),
                        new ManaCostsImpl<>("{1}{U}")
                ), filter, false, SetTargetPointer.PERMANENT, null
        ));
    }

    private ReplicationSpecialist(final ReplicationSpecialist card) {
        super(card);
    }

    @Override
    public ReplicationSpecialist copy() {
        return new ReplicationSpecialist(this);
    }
}
