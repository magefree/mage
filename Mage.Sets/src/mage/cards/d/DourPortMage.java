package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreLeaveWithoutDyingTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DourPortMage extends CardImpl {

    public DourPortMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever one or more other creatures you control leave the battlefield without dying, draw a card.
        this.addAbility(new OneOrMoreLeaveWithoutDyingTriggeredAbility(
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES
        ));

        // {1}{U}, {T}: Return another target creature you control to its owner's hand.
        Ability ability = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private DourPortMage(final DourPortMage card) {
        super(card);
    }

    @Override
    public DourPortMage copy() {
        return new DourPortMage(this);
    }
}
