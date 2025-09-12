package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HellspurBrute extends CardImpl {

    public HellspurBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Affinity for outlaws
        this.addAbility(new AffinityAbility(AffinityType.OUTLAWS));

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private HellspurBrute(final HellspurBrute card) {
        super(card);
    }

    @Override
    public HellspurBrute copy() {
        return new HellspurBrute(this);
    }
}
