package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColfenorTheLastYew extends CardImpl {

    public ColfenorTheLastYew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Colfenor, the Last Yew or another creature you control dies, return up to one other target creature card with lesser toughness from your graveyard to your hand.
        this.addAbility(new ColfenorTheLastYewTriggeredAbility());
    }

    private ColfenorTheLastYew(final ColfenorTheLastYew card) {
        super(card);
    }

    @Override
    public ColfenorTheLastYew copy() {
        return new ColfenorTheLastYew(this);
    }
}

class ColfenorTheLastYewTriggeredAbility extends DiesThisOrAnotherCreatureTriggeredAbility {

    ColfenorTheLastYewTriggeredAbility() {
        super(new ReturnFromGraveyardToHandTargetEffect(), false, StaticFilters.FILTER_CONTROLLED_CREATURE);
    }

    private ColfenorTheLastYewTriggeredAbility(final ColfenorTheLastYewTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiesThisOrAnotherCreatureTriggeredAbility copy() {
        return new ColfenorTheLastYewTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        if (permanent == null) {
            return false;
        }
        FilterCard filterCard = new FilterCreatureCard("creature card with toughness less than " + permanent.getToughness().getValue());
        filterCard.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, permanent.getToughness().getValue()));
        filterCard.add(Predicates.not(new MageObjectReferencePredicate(new MageObjectReference(permanent, game))));
        this.getTargets().clear();
        this.addTarget(new TargetCardInYourGraveyard(0, 1, filterCard));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature you control dies, return up to one other target creature card " +
                "with lesser toughness from your graveyard to your hand.";
    }
}
