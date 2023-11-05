package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class OjerPakpatiqDeepestEpoch extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant spell");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public OjerPakpatiqDeepestEpoch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.secondSideCardClazz = mage.cards.t.TempleOfCyclicalTime.class;

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant spell from your hand, it gains rebound.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD, new OjerPakpatiqDeepestEpochGainReboundEffect(), filter,
                false, SetTargetPointer.SPELL, Zone.HAND
        ));

        // When Ojer Pakpatiq dies, return it to the battlefield tapped and transformed under its owner's control with three time counters on it.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesSourceTriggeredAbility(new OjerPakpatiqDeepestEpochTrigger()));
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
 * Inspired by {@link mage.cards.n.NarsetTranscendent}
 */
class OjerPakpatiqDeepestEpochGainReboundEffect extends ContinuousEffectImpl {

    public OjerPakpatiqDeepestEpochGainReboundEffect() {
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
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (!(sourceObject instanceof Card)) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        game.setEnterWithCounters(sourceObject.getId(), new Counters().addCounter(CounterType.TIME.createInstance(3)));
        controller.moveCards((Card) sourceObject, Zone.BATTLEFIELD, source, game, true, false, true, null);
        return true;
    }
}