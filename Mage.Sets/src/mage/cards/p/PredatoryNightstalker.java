
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class PredatoryNightstalker extends CardImpl {

    public PredatoryNightstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Predatory Nightstalker enters the battlefield, you may have target opponent sacrifice a creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target opponent"), true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PredatoryNightstalker(final PredatoryNightstalker card) {
        super(card);
    }

    @Override
    public PredatoryNightstalker copy() {
        return new PredatoryNightstalker(this);
    }
}
