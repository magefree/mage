package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class KokushoTheEveningStar extends CardImpl {

    public KokushoTheEveningStar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new DiesSourceTriggeredAbility(new LoseLifeOpponentsYouGainLifeLostEffect(5), false));
    }

    private KokushoTheEveningStar(final KokushoTheEveningStar card) {
        super(card);
    }

    @Override
    public KokushoTheEveningStar copy() {
        return new KokushoTheEveningStar(this);
    }

}
