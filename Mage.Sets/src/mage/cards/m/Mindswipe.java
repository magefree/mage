package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Mindswipe extends CardImpl {

    public Mindswipe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{R}");


        // Counter target spell unless its controller pays {X}.  Mindswipe deals X damage to that spell's controller.
        Effect effect = new CounterUnlessPaysEffect(ManacostVariableValue.REGULAR);
        effect.setText("Counter target spell unless its controller pays {X}.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new MindswipeEffect());
    }

    private Mindswipe(final Mindswipe card) {
        super(card);
    }

    @Override
    public Mindswipe copy() {
        return new Mindswipe(this);
    }
}

class MindswipeEffect extends OneShotEffect {

    public MindswipeEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to that spell's controller";
    }

    private MindswipeEffect(final MindswipeEffect effect) {
        super(effect);
    }

    @Override
    public MindswipeEffect copy() {
        return new MindswipeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject object = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (object == null) {
                object = game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.STACK);
            }
            if (object instanceof Spell) {
                Spell spell = (Spell) object;
                Player spellController = game.getPlayer(spell.getControllerId());
                if (spellController != null) {
                    int damage = ManacostVariableValue.REGULAR.calculate(game, source, this);
                    spellController.damage(damage, source.getSourceId(), source, game);
                }
                return true;
            }
        }
        return false;
    }
}
