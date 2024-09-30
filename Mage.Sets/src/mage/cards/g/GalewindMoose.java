package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalewindMoose extends CardImpl {

    public GalewindMoose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private GalewindMoose(final GalewindMoose card) {
        super(card);
    }

    @Override
    public GalewindMoose copy() {
        return new GalewindMoose(this);
    }
}
