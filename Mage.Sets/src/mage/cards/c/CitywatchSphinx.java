package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class CitywatchSphinx extends CardImpl {

    public CitywatchSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Citywatch Sphinx dies, surveil 2.
        this.addAbility(new DiesSourceTriggeredAbility(new SurveilEffect(2)));
    }

    private CitywatchSphinx(final CitywatchSphinx card) {
        super(card);
    }

    @Override
    public CitywatchSphinx copy() {
        return new CitywatchSphinx(this);
    }
}
