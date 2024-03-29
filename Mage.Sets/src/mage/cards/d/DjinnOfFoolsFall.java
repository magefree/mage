package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DjinnOfFoolsFall extends CardImpl {

    public DjinnOfFoolsFall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Plot {3}{U}
        this.addAbility(new PlotAbility("{3}{U}"));
    }

    private DjinnOfFoolsFall(final DjinnOfFoolsFall card) {
        super(card);
    }

    @Override
    public DjinnOfFoolsFall copy() {
        return new DjinnOfFoolsFall(this);
    }
}
