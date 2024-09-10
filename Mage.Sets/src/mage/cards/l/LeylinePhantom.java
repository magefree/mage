package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class LeylinePhantom extends CardImpl {

    public LeylinePhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        //Whenever Leyline Phantom deals combat damage, return it to its owner's hand.
        this.addAbility(new DealsCombatDamageTriggeredAbility(new ReturnToHandSourceEffect(true), false));
    }

    private LeylinePhantom(final LeylinePhantom card) {
        super(card);
    }

    @Override
    public LeylinePhantom copy() {
        return new LeylinePhantom(this);
    }
}
