package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DireStrainBrawler extends CardImpl {

    public DireStrainBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.color.setGreen(true);
        this.transformable = true;
        this.nightCard = true;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Nightbound
        this.addAbility(NightboundAbility.getInstance());
    }

    private DireStrainBrawler(final DireStrainBrawler card) {
        super(card);
    }

    @Override
    public DireStrainBrawler copy() {
        return new DireStrainBrawler(this);
    }
}
