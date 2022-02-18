package mage.cards.g;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class GoblinDarkDwellers extends CardImpl {

    private static final FilterInstantOrSorceryCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public GoblinDarkDwellers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Goblin Dark-Dwellers enters the battlefield, you may cast target instant or sorcery card with converted mana cost 3 or less
        // from your graveyard without paying its mana cost. If that card would be put into your graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GoblinDarkDwellersEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private GoblinDarkDwellers(final GoblinDarkDwellers card) {
        super(card);
    }

    @Override
    public GoblinDarkDwellers copy() {
        return new GoblinDarkDwellers(this);
    }
}

class GoblinDarkDwellersEffect extends OneShotEffect {

    GoblinDarkDwellersEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast target instant or sorcery card with "
                + "mana value 3 or less from your graveyard without paying its mana cost. "
                + "If that card would be put into your graveyard this turn, exile it instead";
    }

    GoblinDarkDwellersEffect(final GoblinDarkDwellersEffect effect) {
        super(effect);
    }

    @Override
    public GoblinDarkDwellersEffect copy() {
        return new GoblinDarkDwellersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
            if (card != null) {
                if (controller.chooseUse(Outcome.PlayForFree, "Cast " + card.getLogName() + '?', source, game)) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                    if (cardWasCast) {
                        ContinuousEffect effect = new GoblinDarkDwellersReplacementEffect(card.getId());
                        effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class GoblinDarkDwellersReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    GoblinDarkDwellersReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        staticText = "If that card would be put into your graveyard this turn, exile it instead";
    }

    GoblinDarkDwellersReplacementEffect(final GoblinDarkDwellersReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public GoblinDarkDwellersReplacementEffect copy() {
        return new GoblinDarkDwellersReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(this.cardId);
    }
}
