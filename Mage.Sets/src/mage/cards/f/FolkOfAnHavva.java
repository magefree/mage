package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class FolkOfAnHavva extends CardImpl {

    public FolkOfAnHavva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Folk of An-Havva blocks, it gets +2/+0 until end of turn.
        this.addAbility(new BlocksSourceTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn, "it")));
    }

    private FolkOfAnHavva(final FolkOfAnHavva card) {
        super(card);
    }

    @Override
    public FolkOfAnHavva copy() {
        return new FolkOfAnHavva(this);
    }
}
