package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.HasteAbility;

/**
 *
 * @author Zelane
 */
public final class AlaundoTheSeer extends CardImpl {

    public AlaundoTheSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{2}{G}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        Ability ability = new SimpleActivatedAbility(new AlaundoTheSeerEffect(), new TapSourceCost());
        this.addAbility(ability);
    }

    private AlaundoTheSeer(final AlaundoTheSeer card) {
        super(card);
    }

    @Override
    public AlaundoTheSeer copy() {
        return new AlaundoTheSeer(this);
    }
}

class AlaundoTheSeerEffect extends OneShotEffect {

    private static final FilterCard inExileFilter = new FilterCard();
    static {
        inExileFilter.add(TargetController.YOU.getOwnerPredicate());
        inExileFilter.add(CounterType.TIME.getPredicate());
    }

    public AlaundoTheSeerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Draw a card, then exile a card from your hand and put a number of time "
                + "counters on it equal to its mana value. It gains \"When the last time counter "
                + "is removed from this card, if it's exiled, you may cast it without paying its "
                + "mana cost. If you cast a creature spell this way, it gains haste until end of "
                + "turn.\" Then remove a time counter from each other card you own in exile.";
    }

    private AlaundoTheSeerEffect(final AlaundoTheSeerEffect effect) {
        super(effect);
    }

    @Override
    public AlaundoTheSeerEffect copy() {
        return new AlaundoTheSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // Draw a card
        controller.drawCards(1, source, game);
        if (controller.getHand().isEmpty()) {
            return false;
        }

        // then exile a card from your hand and put a number of time counters on it equal to its mana value
        TargetCard target = new TargetCardInHand().withChooseHint("to exile");
        controller.chooseTarget(outcome, controller.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        if (!CardUtil.moveCardWithCounter(game, source, controller,
                card, Zone.EXILED, CounterType.TIME.createInstance(card.getManaValue()))) {
            return false;
        }

        // It gains "When the last time counter is removed from this card, if it's exiled, you may cast it without paying its mana cost. 
        // If you cast a creature spell this way, it gains haste until end of turn."
        game.addEffect(new PlayFromExileEffect(new MageObjectReference(card, game)), source);

        // Then remove a time counter from each other card you own in exile.
        game.getExile()
                .getCards(inExileFilter, game)
                .stream()
                .filter(c -> c.getId() != card.getId())
                .forEach(c -> c.removeCounters(CounterType.TIME.getName(), 1, source, game));
        return true;
    }
}

class PlayFromExileEffect extends ContinuousEffectImpl implements SourceEffect {

    MageObjectReference mor;

    public PlayFromExileEffect(MageObjectReference mor) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.mor = mor;
    }

    public PlayFromExileEffect(final PlayFromExileEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public PlayFromExileEffect copy() {
        return new PlayFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(mor.getSourceId());
        if (card != null && mor.refersTo(card, game) && game.getState().getZone(card.getId()) == Zone.EXILED) {
            PlayFromExileAbility ability = new PlayFromExileAbility();
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        } else {
            discard();
        }
        return true;
    }
}

class PlayFromExileAbility extends TriggeredAbilityImpl {

    public PlayFromExileAbility() {
        super(Zone.EXILED, new CastForFreeEffect());
        setRuleVisible(false);
    }

    public PlayFromExileAbility(PlayFromExileAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Card card = game.getCard(this.getSourceId());
            return card != null
                    && game.getState().getZone(card.getId()) == Zone.EXILED
                    && card.getCounters(game).getCount(CounterType.TIME) == 0;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When the last time counter is removed from this card ({this}) you may cast it without paying its mana cost. If you cast a creature spell this way, it gains haste until end of turn.";
    }

    @Override
    public PlayFromExileAbility copy() {
        return new PlayFromExileAbility(this);
    }
}

class CastForFreeEffect extends OneShotEffect {

    public CastForFreeEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast it without paying its mana cost. If you cast a creature spell this way, it gains haste until end of turn.";
    }

    public CastForFreeEffect(final CastForFreeEffect effect) {
        super(effect);
    }

    @Override
    public CastForFreeEffect copy() {
        return new CastForFreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player != null && card != null) {
            if (CardUtil.castSpellWithAttributesForFree(player, source, game, card)) {
                if (card.isCreature(game)) {
                    game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(),
                            Duration.EndOfTurn, null, true).setTargetPointer(new FixedTarget(card, game)), source);
                }
                return true;
            }
        }
        return false;
    }
}
