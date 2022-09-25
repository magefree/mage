package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author nigelzor
 */
public final class GorgonRecluse extends CardImpl {

    public GorgonRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.GORGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Gorgon Recluse blocks or becomes blocked by a nonblack creature, destroy that creature at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect()), true);
        effect.setText("destroy that creature at end of combat");
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(effect, StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));

        // Madness {B}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{B}{B}")));
    }

    private GorgonRecluse(final GorgonRecluse card) {
        super(card);
    }

    @Override
    public GorgonRecluse copy() {
        return new GorgonRecluse(this);
    }
}
