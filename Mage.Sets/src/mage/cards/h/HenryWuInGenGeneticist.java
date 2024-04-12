package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jimga150
 */
public final class HenryWuInGenGeneticist extends CardImpl {

    public static final FilterPermanent filterYourHumans = new FilterCreaturePermanent("Human creatures you control");

    static {
        filterYourHumans.add(TargetController.YOU.getControllerPredicate());
        filterYourHumans.add(SubType.HUMAN.getPredicate());
    }

    public HenryWuInGenGeneticist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Henry Wu, InGen Geneticist and other Human creatures you control have exploit.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(
                new ExploitAbility(), Duration.WhileOnBattlefield, filterYourHumans)));

        // Whenever a creature you control exploits a non-Human creature, draw a card. If the exploited creature had power 3 or greater, create a Treasure token.
        this.addAbility(new HenryWuInGenGeneticistTriggeredAbility());
    }

    private HenryWuInGenGeneticist(final HenryWuInGenGeneticist card) {
        super(card);
    }

    @Override
    public HenryWuInGenGeneticist copy() {
        return new HenryWuInGenGeneticist(this);
    }
}

// Based on ColonelAutumnTriggeredAbility
class HenryWuInGenGeneticistTriggeredAbility extends TriggeredAbilityImpl {

    public static final FilterPermanent filterNonHumans = new FilterCreaturePermanent("non-Human creature");

    static {
        filterNonHumans.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    HenryWuInGenGeneticistTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
//        this.addEffect(new ConditionalOneShotEffect());
        setTriggerPhrase("Whenever a creature you control exploits a " + filterNonHumans.getMessage());
    }

    private HenryWuInGenGeneticistTriggeredAbility(final HenryWuInGenGeneticistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXPLOITED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent exploiter = game.getPermanentOrLKIBattlefield(event.getSourceId());
        Permanent exploited = game.getPermanentOrLKIBattlefield(event.getTargetId());

        return exploiter != null && exploited != null
                && exploiter.isCreature(game)
                && exploiter.isControlledBy(this.getControllerId())
                && filterNonHumans.match(exploited, game);
    }

    @Override
    public HenryWuInGenGeneticistTriggeredAbility copy() {
        return new HenryWuInGenGeneticistTriggeredAbility(this);
    }
}
