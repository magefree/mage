package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnsettlingTwins extends CardImpl {

    public UnsettlingTwins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Unsettling Twins enters, manifest dread.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ManifestDreadEffect()));
    }

    private UnsettlingTwins(final UnsettlingTwins card) {
        super(card);
    }

    @Override
    public UnsettlingTwins copy() {
        return new UnsettlingTwins(this);
    }
}
