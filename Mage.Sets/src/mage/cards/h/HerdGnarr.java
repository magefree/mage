package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class HerdGnarr extends CardImpl {
    
    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");
    
    static {
        filter.add(AnotherPredicate.instance);
    }

    public HerdGnarr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature enters the battlefield under your control, Herd Gnarr gets +2/+2 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), filter));
    }

    private HerdGnarr(final HerdGnarr card) {
        super(card);
    }

    @Override
    public HerdGnarr copy() {
        return new HerdGnarr(this);
    }
}
