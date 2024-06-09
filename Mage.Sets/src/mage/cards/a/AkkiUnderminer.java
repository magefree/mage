

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author Loki
 */
public final class AkkiUnderminer extends CardImpl {

    public AkkiUnderminer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SacrificeEffect(new FilterPermanent(), 1, "that player"), false, true));
    }

    private AkkiUnderminer(final AkkiUnderminer card) {
        super(card);
    }

    @Override
    public AkkiUnderminer copy() {
        return new AkkiUnderminer(this);
    }

}
