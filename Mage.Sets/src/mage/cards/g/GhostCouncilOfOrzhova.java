package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GhostCouncilOfOrzhova extends CardImpl {

    public GhostCouncilOfOrzhova(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Ghost Council of Orzhova enters the battlefield, target opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(1));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {1}, Sacrifice a creature: Exile Ghost Council of Orzhova. Return it to the battlefield under its owner's control at the beginning of the next end step.
        ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(),
                new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private GhostCouncilOfOrzhova(final GhostCouncilOfOrzhova card) {
        super(card);
    }

    @Override
    public GhostCouncilOfOrzhova copy() {
        return new GhostCouncilOfOrzhova(this);
    }

}
