
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public final class OhranViper extends CardImpl {

    public OhranViper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Ohran Viper deals combat damage to a creature, destroy that creature at end of combat.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect("destroy that creature at end of combat"))
                                .setTriggerPhrase(""), true),
                false, 
                true));

        // Whenever Ohran Viper deals combat damage to a player, you may draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), true));
    }

    private OhranViper(final OhranViper card) {
        super(card);
    }

    @Override
    public OhranViper copy() {
        return new OhranViper(this);
    }
}
