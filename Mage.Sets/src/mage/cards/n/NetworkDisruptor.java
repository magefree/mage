package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NetworkDisruptor extends CardImpl {

    public NetworkDisruptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Network Disruptor enters the battlefield, tap target permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private NetworkDisruptor(final NetworkDisruptor card) {
        super(card);
    }

    @Override
    public NetworkDisruptor copy() {
        return new NetworkDisruptor(this);
    }
}
