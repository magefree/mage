package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPlayer;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author Grath
 */
public final class TheLordOfPain extends CardImpl {
    public TheLordOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Your opponents can't gain life.
        this.addAbility(new SimpleStaticAbility(
                new CantGainLifeAllEffect(Duration.WhileOnBattlefield, TargetController.OPPONENT)
        ));

        // Whenever a player casts their first spell each turn, choose another target player. The Lord of Pain deals damage equal to that spell's mana value to the chosen player.
        this.addAbility(new TheLordOfPainTriggeredAbility());
    }

    private TheLordOfPain(final TheLordOfPain card) {
        super(card);
    }

    @Override
    public TheLordOfPain copy() {
        return new TheLordOfPain(this);
    }
}

enum TheLordOfPainPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return game.getState()
                .getWatcher(SpellsCastWatcher.class)
                .getCount(input.getControllerId()) == 1;
    }
}

class TheLordOfPainTriggeredAbility extends SpellCastAllTriggeredAbility {
    private static final FilterSpell filter = new FilterSpell("their first spell each turn");

    static {
        filter.add(TheLordOfPainPredicate.instance);
    }

    public TheLordOfPainTriggeredAbility() {
        super(new TheLordOfPainEffect(), filter, false, SetTargetPointer.PLAYER);
    }

    protected TheLordOfPainTriggeredAbility(final TheLordOfPainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheLordOfPainTriggeredAbility copy() {
        return new TheLordOfPainTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Player controller = game.getPlayer(getControllerId());
            Spell spell = (Spell)getEffects().get(0).getValue("spellCast");
            if (controller != null) {
                FilterPlayer filter2 = new FilterPlayer("another target player");
                filter2.add(Predicates.not(new MageObjectReferencePredicate(spell.getControllerId(), game)));
                TargetPlayer target = new TargetPlayer(1, 1, false, filter2);
                controller.choose(Outcome.Damage, target, this, game);
                getEffects().setTargetPointer(new FixedTarget(target.getFirstTarget()));
                return true;
            }
        }
        return false;
    }
}

class TheLordOfPainEffect extends OneShotEffect {

    TheLordOfPainEffect() {
        super(Outcome.Benefit);
        staticText = "choose another target player. {this} deals damage equal to that spell's mana value to the chosen player";
    }

    private TheLordOfPainEffect(final TheLordOfPainEffect effect) {
        super(effect);
    }

    @Override
    public TheLordOfPainEffect copy() {
        return new TheLordOfPainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell)this.getValue("spellCast");
        if (spell != null) {
            int cost = spell.getManaValue();
            Player target = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (target != null) {
                target.damage(cost, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;    }
}
