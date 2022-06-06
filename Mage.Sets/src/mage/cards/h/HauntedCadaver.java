
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Temba21
 */
public final class HauntedCadaver extends CardImpl {

    public HauntedCadaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Haunted Cadaver deals combat damage to a player, you may sacrifice it. If you do, that player discards three cards.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new SacrificeSourceEffect(), true, true);
        ability.addEffect( new DiscardTargetEffect(3));
        this.addAbility(ability);
        // Morph {1}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private HauntedCadaver(final HauntedCadaver card) {
        super(card);
    }

    @Override
    public HauntedCadaver copy() {
        return new HauntedCadaver(this);
    }
}
