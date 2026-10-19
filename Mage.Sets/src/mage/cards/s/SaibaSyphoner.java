package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

public final class SaibaSyphoner extends CardImpl {

    public SaibaSyphoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionSourceEffect(2, SaibaSyphonerCondition.instance)));

        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("return target instant or sorcery card from your graveyard to your hand"));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(new SaibaSyphonerReplacementEffect()));
    }

    private SaibaSyphoner(final SaibaSyphoner card) {
        super(card);
    }

    @Override
    public SaibaSyphoner copy() {
        return new SaibaSyphoner(this);
    }
}

enum SaibaSyphonerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getHand().count(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game) == 0;
    }

    @Override
    public String toString() {
        return "there are no instant or sorcery cards in your hand";
    }
}

class SaibaSyphonerReplacementEffect extends ReplacementEffectImpl {

    SaibaSyphonerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if {this} would die, shuffle it into your library instead";
    }

    private SaibaSyphonerReplacementEffect(final SaibaSyphonerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SaibaSyphonerReplacementEffect copy() {
        return new SaibaSyphonerReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return ((ZoneChangeEvent) event).isDiesEvent() && event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(source.getSourceId());
        Player owner = card == null ? null : game.getPlayer(card.getOwnerId());
        if (owner == null) {
            return false;
        }
        owner.moveCardToLibraryWithInfo(card, source, game, Zone.BATTLEFIELD, true, true);
        owner.shuffleLibrary(source, game);
        return true;
    }
}
