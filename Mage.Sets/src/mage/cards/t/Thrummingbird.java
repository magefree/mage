package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki, nantuko, North
 */
public final class Thrummingbird extends CardImpl {

    public Thrummingbird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Thrummingbird deals combat damage to a player, proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ProliferateEffect(), false));
    }

    private Thrummingbird(final Thrummingbird card) {
        super(card);
    }

    @Override
    public Thrummingbird copy() {
        return new Thrummingbird(this);
    }
}
