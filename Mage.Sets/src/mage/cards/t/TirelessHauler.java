package mage.cards.t;

import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TirelessHauler extends TransformingDoubleFacedCard {

    public TirelessHauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{4}{G}",
                "Dire-Strain Brawler",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Tireless Hauler
        this.getLeftHalfCard().setPT(4, 5);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Dire-Strain Brawler
        this.getRightHalfCard().setPT(6, 6);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private TirelessHauler(final TirelessHauler card) {
        super(card);
    }

    @Override
    public TirelessHauler copy() {
        return new TirelessHauler(this);
    }
}
