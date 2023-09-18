package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnointedChorister extends CardImpl {

    public AnointedChorister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {4}{W}: Anointed Chorister gets +3/+3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(3, 3, Duration.EndOfTurn), new ManaCostsImpl<>("{4}{W}")
        ));
    }

    private AnointedChorister(final AnointedChorister card) {
        super(card);
    }

    @Override
    public AnointedChorister copy() {
        return new AnointedChorister(this);
    }
}
