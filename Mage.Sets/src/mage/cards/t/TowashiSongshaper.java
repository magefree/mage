package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TowashiSongshaper extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("another artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public TowashiSongshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another artifact enters the battlefield under your control, Towashi Songshaper gets +1/+0 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), filter
        ));
    }

    private TowashiSongshaper(final TowashiSongshaper card) {
        super(card);
    }

    @Override
    public TowashiSongshaper copy() {
        return new TowashiSongshaper(this);
    }
}
