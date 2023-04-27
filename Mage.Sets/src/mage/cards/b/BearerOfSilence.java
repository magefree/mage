
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class BearerOfSilence extends CardImpl {

    public BearerOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast Bearer of Silence, you may pay {1}{C}. If you do, target opponent sacrifices a creature.
        Ability ability = new CastSourceTriggeredAbility(new DoIfCostPaid(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target opponent"), new ManaCostsImpl<>("{1}{C}")));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Bearer of Silence can't block.
        this.addAbility(new CantBlockAbility());
    }

    private BearerOfSilence(final BearerOfSilence card) {
        super(card);
    }

    @Override
    public BearerOfSilence copy() {
        return new BearerOfSilence(this);
    }
}
