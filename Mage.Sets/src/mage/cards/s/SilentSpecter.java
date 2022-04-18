
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SilentSpecter extends CardImpl {

    public SilentSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.SPECTER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Silent Specter deals combat damage to a player, that player discards two cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(2), false, true));
        // Morph {3}{B}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{3}{B}{B}")));
    }

    private SilentSpecter(final SilentSpecter card) {
        super(card);
    }

    @Override
    public SilentSpecter copy() {
        return new SilentSpecter(this);
    }
}
