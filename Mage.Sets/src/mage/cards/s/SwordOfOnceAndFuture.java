package mage.cards.s;

import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwordOfOnceAndFuture extends CardImpl {

    public SwordOfOnceAndFuture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from blue and from black.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.BLUE, ObjectColor.BLACK), AttachmentType.EQUIPMENT
        ).setText("and has protection from blue and from black"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, surveil 2. Then you may cast an instant or sorcery spell with mana value 2 or less from your graveyard without paying its mana cost. If that spell would be put into your graveyard, exile it instead.
        ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new SurveilEffect(2, false),
                "equipped creature", false
        );
        ability.addEffect(new SwordOfOnceAndFutureCastEffect());
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private SwordOfOnceAndFuture(final SwordOfOnceAndFuture card) {
        super(card);
    }

    @Override
    public SwordOfOnceAndFuture copy() {
        return new SwordOfOnceAndFuture(this);
    }
}

class SwordOfOnceAndFutureCastEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    SwordOfOnceAndFutureCastEffect() {
        super(Outcome.Benefit);
        staticText = "Then you may cast an instant or sorcery spell with mana value 2 or less from your graveyard " +
                "without paying its mana cost. If that spell would be put into your graveyard, exile it instead";
    }

    private SwordOfOnceAndFutureCastEffect(final SwordOfOnceAndFutureCastEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfOnceAndFutureCastEffect copy() {
        return new SwordOfOnceAndFutureCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return CardUtil.castSpellWithAttributesForFree(
                player, source, game, new CardsImpl(player.getGraveyard()),
                filter, SwordOfOnceAndFutureTracker.instance
        );
    }
}

enum SwordOfOnceAndFutureTracker implements CardUtil.SpellCastTracker {
    instance;

    @Override
    public boolean checkCard(Card card, Game game) {
        return true;
    }

    @Override
    public void addCard(Card card, Ability source, Game game) {
        game.addEffect(new SwordOfOnceAndFutureReplacementEffect(card, game), source);
    }
}

class SwordOfOnceAndFutureReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    SwordOfOnceAndFutureReplacementEffect(Card card, Game game) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.mor = new MageObjectReference(card.getMainCard(), game);
    }

    private SwordOfOnceAndFutureReplacementEffect(final SwordOfOnceAndFutureReplacementEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public SwordOfOnceAndFutureReplacementEffect copy() {
        return new SwordOfOnceAndFutureReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = mor.getCard(game);
        return controller != null
                && card != null
                && controller.moveCards(card, Zone.EXILED, source, game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(mor.getSourceId());
    }
}
