package mage.abilities.decorator;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LevelX2
 */
public class ConditionalManaEffect extends ManaEffect {

    private BasicManaEffect effect;
    private BasicManaEffect otherwiseEffect;
    private Condition condition;

    public ConditionalManaEffect(BasicManaEffect effect, Condition condition, String text) {
        this(effect, null, condition, text);
    }

    public ConditionalManaEffect(BasicManaEffect effect, BasicManaEffect otherwiseEffect, Condition condition, String text) {
        super();
        this.effect = effect;
        this.otherwiseEffect = otherwiseEffect;
        this.condition = condition;
        this.staticText = text;
    }

    public ConditionalManaEffect(ConditionalManaEffect effect) {
        super(effect);
        this.effect = effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = effect.otherwiseEffect.copy();
        }
        this.condition = effect.condition;
    }

    @Override
    public ConditionalManaEffect copy() {
        return new ConditionalManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            if (condition.apply(game, source)) {
                return effect.getNetMana(game, source);
            } else if (otherwiseEffect != null) {
                return otherwiseEffect.getNetMana(game, source);
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        if (condition.apply(game, source)) {
            mana = effect.getManaTemplate().copy();
        } else if (otherwiseEffect != null) {
            mana = otherwiseEffect.getManaTemplate().copy();
        }
        if (mana.getAny() > 0) {
            int amount = mana.getAny();
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null) {
                return mana;
            }
            ChoiceColor choice = new ChoiceColor(true);
            if (controller.choose(outcome, choice, game)) {
                mana.setAny(0);
                mana.add(choice.getMana(amount));
            }
        }
        return mana;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
