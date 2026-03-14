package mage.cards.t;

import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TavernRuffian extends TransformingDoubleFacedCard {

    public TavernRuffian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARRIOR, SubType.WEREWOLF}, "{3}{R}",
                "Tavern Smasher",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Tavern Ruffian
        this.getLeftHalfCard().setPT(2, 5);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Tavern Smasher
        this.getRightHalfCard().setPT(6, 5);

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private TavernRuffian(final TavernRuffian card) {
        super(card);
    }

    @Override
    public TavernRuffian copy() {
        return new TavernRuffian(this);
    }
}
