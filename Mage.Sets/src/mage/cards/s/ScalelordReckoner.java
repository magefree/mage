package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class ScalelordReckoner extends CardImpl {

    public ScalelordReckoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control becomes the target of a spell or ability an opponent controls, destroy target nonland permanent that player controls.
        this.addAbility(new ScalelordReckonerTriggeredAbility());
    }

    private ScalelordReckoner(final ScalelordReckoner card) {
        super(card);
    }

    @Override
    public ScalelordReckoner copy() {
        return new ScalelordReckoner(this);
    }
}

class ScalelordReckonerTriggeredAbility extends BecomesTargetAnyTriggeredAbility {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Dragon you control");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    ScalelordReckonerTriggeredAbility() {
        super(new DestroyTargetEffect().setText("destroy target nonland permanent that player controls"),
                filter, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.NONE, false);
    }

    private ScalelordReckonerTriggeredAbility(final ScalelordReckonerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScalelordReckonerTriggeredAbility copy() {
        return new ScalelordReckonerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        FilterNonlandPermanent targetFilter = new FilterNonlandPermanent("nonland permanent that player controls");
        targetFilter.add(new ControllerIdPredicate(event.getPlayerId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(targetFilter));
        return true;
    }

}
