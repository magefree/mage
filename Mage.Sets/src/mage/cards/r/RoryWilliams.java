package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;


/**
 *
 * @author Skiwkr
 */
public final class RoryWilliams extends CardImpl {

    public RoryWilliams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Amy Pond
        this.addAbility(new PartnerWithAbility("Amy Pond"));

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // The Last Centurion -- When you cast this spell from anywhere other than exile, exile it with three time counters on it. It gains suspend. Then investigate.
        Ability ability = new RoryWilliamsTriggeredAbility(new RoryWilliamsEffect());
        ability.addEffect(new InvestigateEffect(1).concatBy(", then"));
        this.addAbility(ability);
    }

    private RoryWilliams(final RoryWilliams card) {
        super(card);
    }

    @Override
    public RoryWilliams copy() {
        return new RoryWilliams(this);
    }
}

class RoryWilliamsTriggeredAbility extends TriggeredAbilityImpl {

    public static final String SOURCE_CAST_SPELL_ABILITY = "sourceCastSpellAbility";

    public RoryWilliamsTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public RoryWilliamsTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.STACK, effect, optional);
        this.ruleAtTheTop = true;
        setTriggerPhrase("When you cast this spell from anywhere other than exile, ");
    }

    protected RoryWilliamsTriggeredAbility(final RoryWilliamsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RoryWilliamsTriggeredAbility copy() {
        return new RoryWilliamsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        MageObject spellObject = game.getObject(sourceId);
        if ((!(spellObject instanceof Spell))) {
            return true;
        }
        Spell spell = (Spell) spellObject;
        if (spell.getSpellAbility() != null) {
            getEffects().setValue(SOURCE_CAST_SPELL_ABILITY, spell.getSpellAbility());
        }
        if (spell.getFromZone().toString().equals("exile zone")){
            return false;
        }
        getEffects().setValue("spellCast", spell);
        return true;
    }
}

class RoryWilliamsEffect extends OneShotEffect {

    RoryWilliamsEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile it with three time counters on it and it gains suspend";
    }

    private RoryWilliamsEffect(final RoryWilliamsEffect effect) {
        super(effect);
    }

    @Override
    public RoryWilliamsEffect copy() {
        return new RoryWilliamsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (controller == null || card == null) {
            return false;
        }

        if (game.getState().getZone(card.getId()) == Zone.EXILED) {
            return false;
        }

        card = card.getMainCard();
        UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
        controller.moveCards(card,Zone.EXILED,source, game);
        card.addCounters(CounterType.TIME.createInstance(3), source.getControllerId(), source, game);
        game.addEffect(new GainSuspendEffect(new MageObjectReference(card, game)), source);

        return true;
    }
}