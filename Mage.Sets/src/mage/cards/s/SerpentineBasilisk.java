
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Wehk
 */
public final class SerpentineBasilisk extends CardImpl {

    public SerpentineBasilisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.BASILISK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Serpentine Basilisk deals combat damage to a creature, destroy that creature at end of combat.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect("destroy that creature at end of combat"))
                                .setTriggerPhrase(""), true), false, true));
        // Morph {1}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{G}{G}")));
    }

    private SerpentineBasilisk(final SerpentineBasilisk card) {
        super(card);
    }

    @Override
    public SerpentineBasilisk copy() {
        return new SerpentineBasilisk(this);
    }
}
