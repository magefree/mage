
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Wehk
 */
public final class VindictiveMob extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Saprolings");

    static {
        filter.add(SubType.SAPROLING.getPredicate());
    }

    public VindictiveMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Vindictive Mob enters the battlefield, sacrifice a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, null)));

        // Vindictive Mob can't be blocked by Saprolings.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private VindictiveMob(final VindictiveMob card) {
        super(card);
    }

    @Override
    public VindictiveMob copy() {
        return new VindictiveMob(this);
    }
}
