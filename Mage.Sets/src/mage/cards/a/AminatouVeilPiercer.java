package mage.cards.a;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AminatouVeilPiercer extends CardImpl {

    public AminatouVeilPiercer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, surveil 2.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SurveilEffect(2)));

        // Each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}.
        this.addAbility(new SimpleStaticAbility(new AminatouVeilPiercerEffect()));
        this.addAbility(new AminatouVeilPiercerMiracleTriggeredAbility(), new CardsAmountDrawnThisTurnWatcher());
    }

    private AminatouVeilPiercer(final AminatouVeilPiercer card) {
        super(card);
    }

    @Override
    public AminatouVeilPiercer copy() {
        return new AminatouVeilPiercer(this);
    }
}

class AminatouVeilPiercerEffect extends ContinuousEffectImpl {

    AminatouVeilPiercerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "each enchantment card in your hand has miracle. Its miracle cost is equal to its mana cost reduced by {4}";
    }

    private AminatouVeilPiercerEffect(final AminatouVeilPiercerEffect effect) {
        super(effect);
    }

    @Override
    public AminatouVeilPiercerEffect copy() {
        return new AminatouVeilPiercerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

class AminatouVeilPiercerMiracleTriggeredAbility extends TriggeredAbilityImpl {

    AminatouVeilPiercerMiracleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AminatouVeilPiercerMiracleEffect(), true);
        setTriggerPhrase("Whenever you draw your first card each turn, if it's an enchantment card, ");
    }

    private AminatouVeilPiercerMiracleTriggeredAbility(final AminatouVeilPiercerMiracleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AminatouVeilPiercerMiracleTriggeredAbility copy() {
        return new AminatouVeilPiercerMiracleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        Card card = game.getCard(event.getTargetId());
        if (watcher == null
                || watcher.getAmountCardsDrawn(event.getPlayerId()) != 1
                || card == null
                || !StaticFilters.FILTER_CARD_ENCHANTMENT.match(card, game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(card, game));
        return true;
    }
}

class AminatouVeilPiercerMiracleEffect extends OneShotEffect {

    AminatouVeilPiercerMiracleEffect() {
        super(Outcome.Benefit);
        staticText = "you may reveal that card and cast it for its miracle cost";
    }

    private AminatouVeilPiercerMiracleEffect(final AminatouVeilPiercerMiracleEffect effect) {
        super(effect);
    }

    @Override
    public AminatouVeilPiercerMiracleEffect copy() {
        return new AminatouVeilPiercerMiracleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null || card.getSpellAbility() == null) {
            return false;
        }
        controller.revealCards("Miracle", new CardsImpl(card), game);
        SpellAbility abilityToCast = card.getSpellAbility().copy();
        ManaCosts<ManaCost> costRef = abilityToCast.getManaCostsToPay();
        costRef.clear();
        costRef.add(CardUtil.reduceCost(card.getManaCost(), 4));
        controller.cast(abilityToCast, game, false, new ApprovingObject(source, game));
        return true;
    }
}
