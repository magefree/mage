package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlinkDog extends CardImpl {

    public BlinkDog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Teleport — {3}{W}: Blink Dog phases out.
        this.addAbility(new SimpleActivatedAbility(
                new PhaseOutSourceEffect(), new ManaCostsImpl<>("{3}{W}")
        ).withFlavorWord("Teleport"));
    }

    private BlinkDog(final BlinkDog card) {
        super(card);
    }

    @Override
    public BlinkDog copy() {
        return new BlinkDog(this);
    }
}
