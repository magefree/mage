package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GratefulApparition extends CardImpl {

    public GratefulApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Grateful Apparition deals combat damage to a player or planeswalker, proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
        this.addAbility(new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(new ProliferateEffect(), false));
    }

    private GratefulApparition(final GratefulApparition card) {
        super(card);
    }

    @Override
    public GratefulApparition copy() {
        return new GratefulApparition(this);
    }
}
