package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author miesma
 */
public final class ElrondMoonReader extends CardImpl {

    public static final FilterControlledPermanent filter = new FilterControlledPermanent("other nonland permanents you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(AnotherPredicate.instance);
    }

    public ElrondMoonReader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you activate an ability of a creature,
        // draw a card.
        // This ability triggers only once each turn.
        this.addAbility(new ElrondMoonReaderTriggeredAbility().setTriggersLimitEachTurn(1));

        // Exile up to two other target nonland permanents you control.
        // Return those cards to the battlefield under their owner’s control
        // at the beginning of the next end step.
        Ability flickerAbility = new SimpleActivatedAbility(new ExileReturnBattlefieldNextEndStepTargetEffect().withTextThatCard(true),
                new ManaCostsImpl<>("{5}{U}{U}"));
        flickerAbility.addTarget(new TargetPermanent(0,2, filter));
        this.addAbility(flickerAbility);
    }

    private ElrondMoonReader(final ElrondMoonReader card) {
        super(card);
    }

    @Override
    public ElrondMoonReader copy() {
        return new ElrondMoonReader(this);
    }
}

class ElrondMoonReaderTriggeredAbility extends TriggeredAbilityImpl {


    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature");


    ElrondMoonReaderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
        setTriggerPhrase("Whenever you activate an ability of a creature, ");
    }

    private ElrondMoonReaderTriggeredAbility(final ElrondMoonReaderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ElrondMoonReaderTriggeredAbility copy() {
        return new ElrondMoonReaderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        // can be normal and mana abilities
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY || event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA && game.inCheckPlayableState()) {
            // ignore mana abilities on playable checking
            return false;
        }

        Permanent source = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return source != null
                && event.getPlayerId().equals(getControllerId())
                && filter.match(source, game);
    }
}
