package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TirelessHauler extends CardImpl {

    public TirelessHauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.d.DireStrainBrawler.class;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private TirelessHauler(final TirelessHauler card) {
        super(card);
    }

    @Override
    public TirelessHauler copy() {
        return new TirelessHauler(this);
    }
}
