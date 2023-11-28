package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class DuskRoseReliquary extends CardImpl {

    public DuskRoseReliquary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT)));

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // When Dusk Rose Reliquary enters the battlefield, exile target artifact or creature an opponent controls until Dusk Rose Reliquary leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);

    }

    private DuskRoseReliquary(final DuskRoseReliquary card) {
        super(card);
    }

    @Override
    public DuskRoseReliquary copy() {
        return new DuskRoseReliquary(this);
    }
}
