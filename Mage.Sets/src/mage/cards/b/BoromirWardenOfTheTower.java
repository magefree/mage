package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class BoromirWardenOfTheTower extends CardImpl {

    public BoromirWardenOfTheTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever an opponent casts a spell, if no mana was spent to cast it, counter that spell.
        this.addAbility(new BoromirWardenOfTheTowerTriggeredAbility());

        // Sacrifice Boromir, Warden of the Tower: Creatures you control gain indestructible until end of turn. The Ring tempts you.
        Ability ability = new SimpleActivatedAbility(new GainAbilityAllEffect(
            IndestructibleAbility.getInstance(), Duration.EndOfTurn,
            StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ), new SacrificeSourceCost());
        ability.addEffect(new TheRingTemptsYouEffect());
        this.addAbility(ability);
    }

    private BoromirWardenOfTheTower(final BoromirWardenOfTheTower card) {
        super(card);
    }

    @Override
    public BoromirWardenOfTheTower copy() {
        return new BoromirWardenOfTheTower(this);
    }
}

class BoromirWardenOfTheTowerTriggeredAbility extends TriggeredAbilityImpl {

    public BoromirWardenOfTheTowerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CounterTargetEffect(), false);
    }

    public BoromirWardenOfTheTowerTriggeredAbility(final BoromirWardenOfTheTowerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BoromirWardenOfTheTowerTriggeredAbility copy() {
        return new BoromirWardenOfTheTowerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getStackAbility().getManaCostsToPay().getUsedManaToPay().count() == 0) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell, if no mana was spent to cast it, counter that spell.";
    }
}