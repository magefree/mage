
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class GempalmIncinerator extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Goblins on the battlefield");
    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }
    
    public GempalmIncinerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Cycling {1}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{R}")));
        // When you cycle Gempalm Incinerator, you may have it deal X damage to target creature, where X is the number of Goblins on the battlefield.
        Ability ability = new CycleTriggeredAbility(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)),true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GempalmIncinerator(final GempalmIncinerator card) {
        super(card);
    }

    @Override
    public GempalmIncinerator copy() {
        return new GempalmIncinerator(this);
    }
}
