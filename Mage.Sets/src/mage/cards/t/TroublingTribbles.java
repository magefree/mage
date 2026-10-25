package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TroublingTribbles extends CardImpl {

    public TroublingTribbles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.TRIBBLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // This creature can't block.
        this.addAbility(new CantBlockAbility());

        // At the beginning of your upkeep, create a token that's a copy of this creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenCopySourceEffect()));
    }

    private TroublingTribbles(final TroublingTribbles card) {
        super(card);
    }

    @Override
    public TroublingTribbles copy() {
        return new TroublingTribbles(this);
    }
}
