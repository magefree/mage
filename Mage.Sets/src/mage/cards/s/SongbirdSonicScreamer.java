package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class SongbirdSonicScreamer extends CardImpl {

    public SongbirdSonicScreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Discard a card: Songbird gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
            new DiscardCardCost()
        ));
    }

    private SongbirdSonicScreamer(final SongbirdSonicScreamer card) {
        super(card);
    }

    @Override
    public SongbirdSonicScreamer copy() {
        return new SongbirdSonicScreamer(this);
    }
}
