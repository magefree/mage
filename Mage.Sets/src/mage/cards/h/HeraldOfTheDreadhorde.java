package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeraldOfTheDreadhorde extends CardImpl {

    public HeraldOfTheDreadhorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Herald of the Dreadhorde dies, amass 2.
        this.addAbility(new DiesSourceTriggeredAbility(new AmassEffect(2, SubType.ZOMBIE)));
    }

    private HeraldOfTheDreadhorde(final HeraldOfTheDreadhorde card) {
        super(card);
    }

    @Override
    public HeraldOfTheDreadhorde copy() {
        return new HeraldOfTheDreadhorde(this);
    }
}
