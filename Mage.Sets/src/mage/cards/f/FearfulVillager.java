package mage.cards.f;

import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearfulVillager extends TransformingDoubleFacedCard {

    public FearfulVillager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{R}",
                "Fearsome Werewolf",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Fearful Villager
        this.getLeftHalfCard().setPT(2, 3);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility());

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Fearsome Werewolf
        this.getRightHalfCard().setPT(4, 3);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private FearfulVillager(final FearfulVillager card) {
        super(card);
    }

    @Override
    public FearfulVillager copy() {
        return new FearfulVillager(this);
    }
}
