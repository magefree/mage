package mage.cards.i;

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
public final class InnocuousRat extends CardImpl {

    public InnocuousRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Innocuous Rat dies, manifest dread.
        this.addAbility(new DiesSourceTriggeredAbility(new ManifestDreadEffect()));
    }

    private InnocuousRat(final InnocuousRat card) {
        super(card);
    }

    @Override
    public InnocuousRat copy() {
        return new InnocuousRat(this);
    }
}
