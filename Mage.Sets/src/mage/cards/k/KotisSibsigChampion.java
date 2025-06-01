package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.CastFromGraveyardOnceDuringEachOfYourTurnAbility;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class KotisSibsigChampion extends CardImpl {

    private static final FilterCard filter = new FilterCard("other cards");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public KotisSibsigChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Once during each of your turns, you may cast a creature spell from your graveyard by exiling three other cards from your graveyard in addition to paying its other costs.
        Cost cost = new ExileFromGraveCost(new TargetCardInYourGraveyard(3, filter));
        cost.setText(cost.getText().replace("exile", "exiling"));
        this.addAbility(new CastFromGraveyardOnceDuringEachOfYourTurnAbility(StaticFilters.FILTER_CARD_A_CREATURE_SPELL, cost));

        // Whenever one or more creatures you control enter, if one or more of them entered from a graveyard or was cast from a graveyard, put two +1/+1 counters on Kotis.
        this.addAbility(new KotisSibsigTriggeredAbility());
    }

    private KotisSibsigChampion(final KotisSibsigChampion card) {
        super(card);
    }

    @Override
    public KotisSibsigChampion copy() {
        return new KotisSibsigChampion(this);
    }
}

class KotisSibsigTriggeredAbility extends EntersBattlefieldOneOrMoreTriggeredAbility {

    KotisSibsigTriggeredAbility() {
        super(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES, TargetController.YOU);
        setTriggerPhrase("Whenever one or more creatures you control enter, "
                + "if one or more of them entered from a graveyard or was cast from a graveyard, ");
    }

    private KotisSibsigTriggeredAbility(final KotisSibsigTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KotisSibsigTriggeredAbility copy() {
        return new KotisSibsigTriggeredAbility(this);
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (super.checkEvent(event, game)) {
            Zone fromZone = event.getFromZone();
            if (fromZone == Zone.GRAVEYARD) {
                return true;
            }
            if (fromZone == Zone.STACK) {
                Permanent permanent = event.getTarget();
                Spell spell = game.getSpellOrLKIStack(permanent.getId());
                return spell != null && spell.getFromZone() == Zone.GRAVEYARD;
            }
        }
        return false;
    }
}