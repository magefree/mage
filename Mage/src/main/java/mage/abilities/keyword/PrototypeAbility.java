package mage.abilities.keyword;

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
import mage.game.stack.Spell;

/**
 * @author TheElk801, Susucr
 */
public class PrototypeAbility extends SpellAbility {

    private final int power;
    private final int toughness;

    public PrototypeAbility(Card card, String manaString, int power, int toughness) {
        super(new ManaCostsImpl<>(manaString), card.getName() + " with prototype", Zone.HAND, SpellAbilityType.PROTOTYPE);
        this.setTiming(TimingRule.SORCERY);
        this.addSubAbility(new SimpleStaticAbility(
                Zone.ALL, new PrototypeEffect(power, toughness, manaString)
        ).setRuleVisible(false));

        this.power = power;
        this.toughness = toughness;

        setRuleAtTheTop(true);
    }

    private PrototypeAbility(final PrototypeAbility ability) {
        super(ability);
        this.power = ability.power;
        this.toughness = ability.toughness;
    }

    @Override
    public PrototypeAbility copy() {
        return new PrototypeAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Prototype ");
        sb.append(this.manaCosts.getText());
        sb.append(" &mdash; ").append(this.power).append("/").append(this.toughness);
        sb.append(" <i>(You may cast this spell with different mana cost, color, and size. It keeps its abilities and types.)</i>");
        return sb.toString();
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
        switch (game.getState().getZone(source.getSourceId())) {
            case BATTLEFIELD:
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent == null || !permanent.isPrototyped()) {
                    return false;
                }
                permanent.setManaCost(new ManaCostsImpl<>(manaString));
                permanent.getColor(game).setColor(color);
                permanent.getPower().setModifiedBaseValue(power);
                permanent.getToughness().setModifiedBaseValue(toughness);
                return true;
            case STACK:
                Spell spell = game.getSpell(source.getSourceId());
                if (spell == null || !spell.isPrototyped()) {
                    return false;
                }
                game.getState().getCreateMageObjectAttribute(spell, game).getColor().setColor(color);
                spell.getPower().setModifiedBaseValue(power);
                spell.getToughness().setModifiedBaseValue(toughness);
                spell.setManaCost(new ManaCostsImpl<>(manaString));
                return true;
            default:
                Card card = game.getCard(source.getSourceId());
                if (card == null) {
                    return false;
                }
                card.getPower().resetToBaseValue();
                card.getToughness().resetToBaseValue();
                return true;
        }
    }
}
