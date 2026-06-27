package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ArtifactSourcePredicate;
import mage.target.common.TargetActivatedOrTriggeredAbility;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ScientistSupremeOfAIM extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("activated or triggered ability you control from an artifact source");

    static {
        filter.add(ArtifactSourcePredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ScientistSupremeOfAIM(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SCIENTIST, SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Pay 2 life: Copy target activated or triggered ability you control from an artifact source. You may choose new targets for the copy. Activate only during your turn and only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new CopyTargetStackObjectEffect(), new PayLifeCost(2), 1, MyTurnCondition.instance);
        ability.addTarget(new TargetActivatedOrTriggeredAbility(filter));
        this.addAbility(ability.addHint(MyTurnHint.instance));
    }

    private ScientistSupremeOfAIM(final ScientistSupremeOfAIM card) {
        super(card);
    }

    @Override
    public ScientistSupremeOfAIM copy() {
        return new ScientistSupremeOfAIM(this);
    }
}
