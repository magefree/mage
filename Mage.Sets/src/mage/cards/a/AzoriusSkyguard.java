package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AzoriusSkyguard extends CardImpl {

    public AzoriusSkyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Creatures your opponents control get -1/-0.
        this.addAbility(new SimpleStaticAbility(new BoostOpponentsEffect(-1, 0, Duration.WhileOnBattlefield)));
    }

    private AzoriusSkyguard(final AzoriusSkyguard card) {
        super(card);
    }

    @Override
    public AzoriusSkyguard copy() {
        return new AzoriusSkyguard(this);
    }
}
