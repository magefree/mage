package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PenregonStrongbull extends CardImpl {

    public PenregonStrongbull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}, Sacrifice an artifact: Penregon Strongbull gets +1/+1 until end of turn and deals 1 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));
        ability.addEffect(new DamagePlayersEffect(1, TargetController.OPPONENT)
                .setText("and deals 1 damage to each opponent"));
        this.addAbility(ability);
    }

    private PenregonStrongbull(final PenregonStrongbull card) {
        super(card);
    }

    @Override
    public PenregonStrongbull copy() {
        return new PenregonStrongbull(this);
    }
}
