package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KaervekTheSpiteful extends CardImpl {

    public KaervekTheSpiteful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Other creatures get -1/-1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                -1, -1, Duration.WhileOnBattlefield, true
        ).setText("other creatures get -1/-1")));
    }

    private KaervekTheSpiteful(final KaervekTheSpiteful card) {
        super(card);
    }

    @Override
    public KaervekTheSpiteful copy() {
        return new KaervekTheSpiteful(this);
    }
}
