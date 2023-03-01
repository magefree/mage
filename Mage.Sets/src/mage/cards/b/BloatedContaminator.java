package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class BloatedContaminator extends CardImpl {

    public BloatedContaminator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // Whenever Bloated Contaminator deals combat damage to a player, proliferate.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ProliferateEffect(), false));
    }

    private BloatedContaminator(final BloatedContaminator card) {
        super(card);
    }

    @Override
    public BloatedContaminator copy() {
        return new BloatedContaminator(this);
    }
}
