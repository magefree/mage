
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.UktabiKongApeToken;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LoneFox
 */
public final class UktabiKong extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Apes you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.APE.getPredicate());
    }

    public UktabiKong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}{G}");
        this.subtype.add(SubType.APE);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Uktabi Kong enters the battlefield, destroy all artifacts.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(new FilterArtifactPermanent("artifacts")), false));

        // Tap two untapped Apes you control: Create a 1/1 green Ape creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new UktabiKongApeToken()), new TapTargetCost(new TargetControlledPermanent(2, 2, filter, true))));
    }

    private UktabiKong(final UktabiKong card) {
        super(card);
    }

    @Override
    public UktabiKong copy() {
        return new UktabiKong(this);
    }
}
