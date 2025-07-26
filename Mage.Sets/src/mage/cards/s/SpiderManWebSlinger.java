package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.WebSlingingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderManWebSlinger extends CardImpl {

    public SpiderManWebSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Web-slinging {W}
        this.addAbility(new WebSlingingAbility(this, "{W}"));
    }

    private SpiderManWebSlinger(final SpiderManWebSlinger card) {
        super(card);
    }

    @Override
    public SpiderManWebSlinger copy() {
        return new SpiderManWebSlinger(this);
    }
}
