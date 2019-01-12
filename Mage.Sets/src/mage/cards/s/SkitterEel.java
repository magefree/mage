package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.AdaptAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkitterEel extends CardImpl {

    public SkitterEel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{U}: Adapt 2.
        this.addAbility(new AdaptAbility(2, "{2}{U}"));
    }

    private SkitterEel(final SkitterEel card) {
        super(card);
    }

    @Override
    public SkitterEel copy() {
        return new SkitterEel(this);
    }
}
