package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EcstaticAwakener extends CardImpl {

    public EcstaticAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.a.AwokenDemon.class;

        // {2}{B}, Sacrifice another creature: Draw a card, then transform Ecstatic Awakener. Activate only once each turn.
        this.addAbility(new TransformAbility());
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addEffect(new TransformSourceEffect(true).concatBy(", then"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        this.addAbility(ability);
    }

    private EcstaticAwakener(final EcstaticAwakener card) {
        super(card);
    }

    @Override
    public EcstaticAwakener copy() {
        return new EcstaticAwakener(this);
    }
}
