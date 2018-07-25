package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Viper extends CardImpl {

    public Viper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {B}, {T}: Target creature an opponent controls attacks you this turn if able.
        // {G}, {T}: Target creature blocks this turn if able.
    }

    public Viper(final Viper card) {
        super(card);
    }

    @Override
    public Viper copy() {
        return new Viper(this);
    }
}
