
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.PoisonousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author dokkaebi
 */
public final class VirulentSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public VirulentSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Sliver creatures have poisonous 1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(new PoisonousAbility(1),
                        Duration.WhileOnBattlefield, filter,
                        "All Sliver creatures have poisonous 1. <i>(Whenever a Sliver deals combat damage to a player, that player gets a poison counter.)</i>")));
    }

    private VirulentSliver(final VirulentSliver card) {
        super(card);
    }

    @Override
    public VirulentSliver copy() {
        return new VirulentSliver(this);
    }
}
