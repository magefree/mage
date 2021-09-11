package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarvesttideAssailant extends CardImpl {

    public HarvesttideAssailant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setRed(true);
        this.transformable = true;
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Nightbound
        this.addAbility(NightboundAbility.getInstance());
    }

    private HarvesttideAssailant(final HarvesttideAssailant card) {
        super(card);
    }

    @Override
    public HarvesttideAssailant copy() {
        return new HarvesttideAssailant(this);
    }
}
