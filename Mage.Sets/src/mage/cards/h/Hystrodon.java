
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Hystrodon extends CardImpl {

    public Hystrodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Hystrodon deals combat damage to a player, you may draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), true));
        // Morph {1}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{1}{G}{G}")));
    }

    private Hystrodon(final Hystrodon card) {
        super(card);
    }

    @Override
    public Hystrodon copy() {
        return new Hystrodon(this);
    }
}
