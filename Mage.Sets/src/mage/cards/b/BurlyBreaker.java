package mage.cards.b;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurlyBreaker extends TransformingDoubleFacedCard {

    public BurlyBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{3}{G}{G}",
                "Dire-Strain Demolisher",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G");

        // Burly Breaker
        this.getLeftHalfCard().setPT(6, 5);

        // Ward {1}
        this.getLeftHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Dire-Strain Demolisher
        this.getRightHalfCard().setPT(8, 7);

        // Ward {3}
        this.getRightHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{3}")));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private BurlyBreaker(final BurlyBreaker card) {
        super(card);
    }

    @Override
    public BurlyBreaker copy() {
        return new BurlyBreaker(this);
    }
}
