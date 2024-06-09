package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOpponentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class ShatteredAngel extends CardImpl {

    public ShatteredAngel (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a land enters the battlefield under an opponent's control, you may gain 3 life.
        this.addAbility(new EntersBattlefieldOpponentTriggeredAbility(new GainLifeEffect(3), StaticFilters.FILTER_LAND_A, true));
    }

    private ShatteredAngel(final ShatteredAngel card) {
        super(card);
    }

    @Override
    public ShatteredAngel copy() {
        return new ShatteredAngel(this);
    }

}
