package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BashfulBeastie extends CardImpl {

    public BashfulBeastie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Bashful Beastie dies, manifest dread.
        this.addAbility(new DiesSourceTriggeredAbility(new ManifestDreadEffect()));
    }

    private BashfulBeastie(final BashfulBeastie card) {
        super(card);
    }

    @Override
    public BashfulBeastie copy() {
        return new BashfulBeastie(this);
    }
}
