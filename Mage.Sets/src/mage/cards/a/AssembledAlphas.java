package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class AssembledAlphas extends CardImpl {

    public AssembledAlphas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Assembled Alphas blocks or becomes blocked by a creature, Assembled Alphas deals 3 damage to that creature and 3 damage to that creature's controller.
        Ability ability = new BlocksOrBlockedByCreatureSourceTriggeredAbility(new DamageTargetEffect(3, true, "that creature"));
        ability.addEffect(new DamageTargetControllerEffect(3).setText("and 3 damage to that creature's controller"));
        this.addAbility(ability);
    }

    private AssembledAlphas(final AssembledAlphas card) {
        super(card);
    }

    @Override
    public AssembledAlphas copy() {
        return new AssembledAlphas(this);
    }
}
