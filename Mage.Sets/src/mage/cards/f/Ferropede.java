
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class Ferropede extends CardImpl {

    public Ferropede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ferropede is unblockable.
        this.addAbility(new CantBeBlockedSourceAbility());
        // Whenever Ferropede deals combat damage to a player, you may remove a counter from target permanent.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new RemoveCounterTargetEffect(), true);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private Ferropede(final Ferropede card) {
        super(card);
    }

    @Override
    public Ferropede copy() {
        return new Ferropede(this);
    }
}
