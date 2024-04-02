package mage.cards.f;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SuspectedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SuspectSourceEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author DominionSpy
 */
public final class FranticScapegoat extends CardImpl {

    public FranticScapegoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Frantic Scapegoat enters the battlefield, suspect it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SuspectSourceEffect()));

        // Whenever one or more other creatures enter the battlefield under your control, if Frantic Scapegoat is suspected, you may suspect one of the other creatures. If you do, Frantic Scapegoat is no longer suspected.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new FranticScapegoatAbility(), SuspectedCondition.instance,
                "Whenever one or more other creatures enter the battlefield under your control, " +
                        "if Frantic Scapegoat is suspected, you may suspect one of the other creatures. " +
                        "If you do, Frantic Scapegoat is no longer suspected.")
        );
    }

    private FranticScapegoat(final FranticScapegoat card) {
        super(card);
    }

    @Override
    public FranticScapegoat copy() {
        return new FranticScapegoat(this);
    }
}

class FranticScapegoatAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("other creatures you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    FranticScapegoatAbility() {
        super(Zone.BATTLEFIELD, new FranticScapegoatEffect(), false);
    }

    private FranticScapegoatAbility(final FranticScapegoatAbility ability) {
        super(ability);
    }

    @Override
    public FranticScapegoatAbility copy() {
        return new FranticScapegoatAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        Player controller = game.getPlayer(this.controllerId);
        if (zEvent.getToZone() != Zone.BATTLEFIELD || controller == null
                || !controller.getId().equals(zEvent.getPlayerId())) {
            return false;
        }

        List<Permanent> creatures = zEvent.getCards()
                .stream()
                .map(Card::getId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> filter.match(permanent, controllerId, this, game))
                .collect(Collectors.toList());
        getEffects().setTargetPointer(new FixedTargets(creatures, game));
        return true;
    }
}

class FranticScapegoatEffect extends OneShotEffect {

    FranticScapegoatEffect() {
        super(Outcome.Benefit);
    }

    private FranticScapegoatEffect(final FranticScapegoatEffect effect) {
        super(effect);
    }

    @Override
    public Effect copy() {
        return new FranticScapegoatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        List<UUID> targets = getTargetPointer().getTargets(game, source);
        if (targets.isEmpty()) {
            return false;
        }

        Cards cards = new CardsImpl();
        cards.addAll(targets);
        TargetCard target = new TargetCard(0, 1,
                Zone.BATTLEFIELD, StaticFilters.FILTER_CARD_CREATURE);

        if (controller.choose(outcome, cards, target, source, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.setSuspected(true, game, source);
                Permanent scapegoat = source.getSourcePermanentIfItStillExists(game);
                if (scapegoat != null) {
                    scapegoat.setSuspected(false, game, source);
                }
            }
        }

        return true;
    }
}
