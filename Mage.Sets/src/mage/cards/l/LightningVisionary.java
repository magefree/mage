package mage.cards.l;

import mage.MageInt;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightningVisionary extends CardImpl {

    public LightningVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private LightningVisionary(final LightningVisionary card) {
        super(card);
    }

    @Override
    public LightningVisionary copy() {
        return new LightningVisionary(this);
    }
}
