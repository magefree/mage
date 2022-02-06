
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class NinjaOfTheDeepHours extends CardImpl {

    public NinjaOfTheDeepHours(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ninjutsu {1}{U} ({1}{U}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{1}{U}"));

        // Whenever Ninja of the Deep Hours deals combat damage to a player, you may draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), true, false));
    }

    private NinjaOfTheDeepHours(final NinjaOfTheDeepHours card) {
        super(card);
    }

    @Override
    public NinjaOfTheDeepHours copy() {
        return new NinjaOfTheDeepHours(this);
    }
}