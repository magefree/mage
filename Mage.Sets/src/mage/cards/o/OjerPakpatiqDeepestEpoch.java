package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReboundAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.cards.n.NarsetTranscendent;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OjerPakpatiqDeepestEpoch extends TransformingDoubleFacedCard {

    private static final FilterSpell filter = new FilterSpell("an instant spell");
    private static final Condition condition = new SourceHasCounterCondition(CounterType.TIME, ComparisonType.EQUAL_TO, 0);

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public OjerPakpatiqDeepestEpoch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{2}{U}{U}",
                "Temple of Cyclical Time",
                new SuperType[]{}, new CardType[]{CardType.LAND}, new SubType[]{}, "");

        // Ojer Pakpatiq, Deepest Epoch
        this.getLeftHalfCard().setPT(4, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant spell from your hand, it gains rebound.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD, new OjerPakpatiqDeepestEpochGainReboundEffect(), filter,
                false, SetTargetPointer.SPELL, Zone.HAND
        ));

        // When Ojer Pakpatiq dies, return it to the battlefield tapped and transformed under its owner's control with three time counters on it.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new OjerPakpatiqDeepestEpochTrigger()));

        // Temple of Cyclical Time
        // {T}: Add {U}. Remove a time counter from Temple of Cyclical Time.
        Ability ability = new BlueManaAbility();
        ability.addEffect(new RemoveCounterSourceEffect(CounterType.TIME.createInstance()));
        this.getRightHalfCard().addAbility(ability);

        // {2}{U}, {T}: Transform Temple of Cyclical Time. Activate only if it has no time counters on it and only as a sorcery.
        ability = new ActivateIfConditionActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{2}{U}"), condition
        ).setTiming(TimingRule.SORCERY);
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability);
    }

    private OjerPakpatiqDeepestEpoch(final OjerPakpatiqDeepestEpoch card) {
        super(card);
    }

    @Override
    public OjerPakpatiqDeepestEpoch copy() {
        return new OjerPakpatiqDeepestEpoch(this);
    }
}

/**
 * Inspired by {@link NarsetTranscendent}
 */
class OjerPakpatiqDeepestEpochGainReboundEffect extends ContinuousEffectImpl {

    OjerPakpatiqDeepestEpochGainReboundEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it gains rebound";
    }

    private OjerPakpatiqDeepestEpochGainReboundEffect(final OjerPakpatiqDeepestEpochGainReboundEffect effect) {
        super(effect);
    }

    @Override
    public OjerPakpatiqDeepestEpochGainReboundEffect copy() {
        return new OjerPakpatiqDeepestEpochGainReboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            discard();
            return false;
        }

        Card card = spell.getCard();
        if (card == null) {
            return false;
        }

        addReboundAbility(card, game);
        return true;
    }

    private void addReboundAbility(Card card, Game game) {
        boolean found = false;
        for (Ability ability : card.getAbilities(game)) {
            if (ability instanceof ReboundAbility) {
                found = true;
                break;
            }
        }
        if (!found) {
            Ability ability = new ReboundAbility();
            game.getState().addOtherAbility(card, ability);
        }
    }
}

// Inspired by Edgar, Charmed Groom
class OjerPakpatiqDeepestEpochTrigger extends OneShotEffect {

    OjerPakpatiqDeepestEpochTrigger() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield tapped and transformed under its owner's control with three time counters on it";
    }

    private OjerPakpatiqDeepestEpochTrigger(final OjerPakpatiqDeepestEpochTrigger effect) {
        super(effect);
    }

    @Override
    public OjerPakpatiqDeepestEpochTrigger copy() {
        return new OjerPakpatiqDeepestEpochTrigger(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        if (controller == null || card == null) {
            return false;
        }
        game.getState().setValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        game.setEnterWithCounters(card.getId(), new Counters().addCounter(CounterType.TIME.createInstance(3)));
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
        return true;
    }
}
