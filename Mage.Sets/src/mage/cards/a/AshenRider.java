
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class AshenRider extends CardImpl {

    public AshenRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}{B}{B}");
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Ashen Rider enters the battlefield or dies, exile target permanent.
        Ability ability = new EntersBattlefieldOrDiesSourceTriggeredAbility(new ExileTargetEffect(), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private AshenRider(final AshenRider card) {
        super(card);
    }

    @Override
    public AshenRider copy() {
        return new AshenRider(this);
    }
}
