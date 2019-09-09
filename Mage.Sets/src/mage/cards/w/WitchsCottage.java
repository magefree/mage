package mage.cards.w;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitchsCottage extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.SWAMP);
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.FEWER_THAN, 3);

    public WitchsCottage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {B}.)
        this.addAbility(new BlackManaAbility());

        // Witch's Cottage enters the battlefield tapped unless you control three or more other Swamps.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new TapSourceEffect(), condition),
                "tapped unless you control three or more other Swamps"
        ));

        // When Witch's Cottage enters the battlefield untapped, you may put target creature card from your graveyard on top of your library.
        this.addAbility(new WitchsCottageTriggeredAbility());
    }

    private WitchsCottage(final WitchsCottage card) {
        super(card);
    }

    @Override
    public WitchsCottage copy() {
        return new WitchsCottage(this);
    }
}

class WitchsCottageTriggeredAbility extends EntersBattlefieldTriggeredAbility {

    WitchsCottageTriggeredAbility() {
        super(new PutOnLibraryTargetEffect(true), true);
        this.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private WitchsCottageTriggeredAbility(final WitchsCottageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WitchsCottageTriggeredAbility copy() {
        return new WitchsCottageTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isTapped();
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield untapped, you may put target creature card from your graveyard on top of your library.";
    }
}