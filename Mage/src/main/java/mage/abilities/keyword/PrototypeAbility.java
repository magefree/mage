package mage.abilities.keyword;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801, Susucr, notgreat
 */
public class PrototypeAbility extends SpellAbility {

    private final int power;
    private final int toughness;
    private final String manaString;
    private final String rule;

    public PrototypeAbility(Card card, String manaString, int power, int toughness) {
        super(new ManaCostsImpl<>(manaString), card.getName());
        this.setSpellAbilityCastMode(SpellAbilityCastMode.PROTOTYPE);
        this.setTiming(TimingRule.SORCERY);
        this.addSubAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new PrototypeEffect(power, toughness, manaString)
        ).setRuleVisible(false));
        this.rule = "Prototype " + manaString + " &mdash; " + power + "/" + toughness +
                " <i>(You may cast this spell with different mana cost, color, and size. It keeps its abilities and types.)</i>";
        setRuleAtTheTop(true);
        this.power = power;
        this.toughness = toughness;
        this.manaString = manaString;
    }

    private PrototypeAbility(final PrototypeAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.power = ability.power;
        this.toughness = ability.toughness;
        this.manaString = ability.manaString;
    }

    @Override
    public PrototypeAbility copy() {
        return new PrototypeAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }

    //based on TransformAbility
    public Card prototypeCardSpell(Card original) {
        Card newCard = original.copy();
        newCard.setManaCost(new ManaCostsImpl<>(manaString));
        newCard.getPower().setModifiedBaseValue(power);
        newCard.getToughness().setModifiedBaseValue(toughness);
        newCard.getColor().setColor(new ObjectColor(manaString));
        return newCard;
    }

    public void prototypePermanent(MageObject targetObject, Game game) {
        if (targetObject instanceof Permanent) {
            ((Permanent)targetObject).setPrototyped(true);
        }
        targetObject.getColor(game).setColor(new ObjectColor(manaString));
        targetObject.setManaCost(new ManaCostsImpl<>(manaString));
        targetObject.getPower().setModifiedBaseValue(power);
        targetObject.getToughness().setModifiedBaseValue(toughness);
    }
}

class PrototypeEffect extends ContinuousEffectImpl {

    private final int power;
    private final int toughness;
    private final String manaString;
    private final ObjectColor color;

    PrototypeEffect(int power, int toughness, String manaString) {
        super(Duration.EndOfGame, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.Benefit);
        this.power = power;
        this.toughness = toughness;
        this.manaString = manaString;
        this.color = new ObjectColor(manaString);
    }

    private PrototypeEffect(final PrototypeEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.manaString = effect.manaString;
        this.color = effect.color;
    }

    @Override
    public PrototypeEffect copy() {
        return new PrototypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || !permanent.isPrototyped()) {
            return false;
        }
        permanent.setManaCost(new ManaCostsImpl<>(manaString));
        permanent.getColor(game).setColor(color);
        permanent.getPower().setModifiedBaseValue(power);
        permanent.getToughness().setModifiedBaseValue(toughness);
        return true;
    }
}