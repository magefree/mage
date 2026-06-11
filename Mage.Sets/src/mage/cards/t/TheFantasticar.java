package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FantasticarConstructToken;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author lilacLunatic
 */
public final class TheFantasticar extends CardImpl {

    public TheFantasticar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.supertype.add(SuperType.LEGENDARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, you may have The Fantasticar become an artifact creature until end of turn.
        Effect effect = new AddCardTypeSourceEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("have {this} become an artifact creature until end of turn.");
        Ability becomeCreatureAbility = new SpellCastControllerTriggeredAbility(effect, StaticFilters.FILTER_SPELL_A_NON_CREATURE, true);
        this.addAbility(becomeCreatureAbility);
        this.addAbility(new TheFantasticarTriggeredAbility());
    }

    private TheFantasticar(final TheFantasticar card) {
        super(card);
    }

    @Override
    public TheFantasticar copy() {
        return new TheFantasticar(this);
    }
}

class TheFantasticarTriggeredAbility extends TriggeredAbilityImpl {

    public TheFantasticarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(
                    new CreateTokenEffect(new FantasticarConstructToken(), 4), new SacrificeSourceCost())
                .setText("you may sacrifice {this}. " +
                         "If you do, create four 4/4 colorless Construct " +
                         "artifact creature tokens with flying and haste."));
        setTriggerPhrase("Whenever you cast your fourth noncreature spell each turn, ");
    }

    private TheFantasticarTriggeredAbility(final TheFantasticarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheFantasticarTriggeredAbility copy() {
        return new TheFantasticarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            return watcher != null && 
                StaticFilters.FILTER_SPELL_NON_CREATURE.match(game.getSpell(event.getTargetId()), game) &&
                watcher.getSpellsCastThisTurn(this.getControllerId())
                .stream()
                .filter(spell -> !spell.isCreature(null))
                .toList()
                .size() == 4;
        }
        return false;
    }
}
