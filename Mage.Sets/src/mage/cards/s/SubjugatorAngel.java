package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class SubjugatorAngel extends CardImpl {

    public SubjugatorAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Subjugator Angel enters the battlefield, tap all creatures your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES)));
    }

    private SubjugatorAngel(final SubjugatorAngel card) {
        super(card);
    }

    @Override
    public SubjugatorAngel copy() {
        return new SubjugatorAngel(this);
    }
}
