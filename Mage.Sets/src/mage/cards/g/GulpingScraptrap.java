package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GulpingScraptrap extends CardImpl {

    public GulpingScraptrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Gulping Scraptrap enters the battlefield or dies, proliferate.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new ProliferateEffect(), false));
    }

    private GulpingScraptrap(final GulpingScraptrap card) {
        super(card);
    }

    @Override
    public GulpingScraptrap copy() {
        return new GulpingScraptrap(this);
    }
}
