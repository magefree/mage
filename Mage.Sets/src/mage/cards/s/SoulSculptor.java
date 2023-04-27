package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SoulSculptor extends CardImpl {

    private static final String rule = "Target creature becomes an enchantment and loses all abilities until a player casts a creature spell.";

    public SoulSculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W}, {tap}: Target creature becomes an enchantment and loses all abilities until a player casts a creature spell.
        Ability ability = new SimpleActivatedAbility(new ConditionalContinuousEffect(new SoulSculptorEffect(), SoulSculptorCondition.instance, rule), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private SoulSculptor(final SoulSculptor card) {
        super(card);
    }

    @Override
    public SoulSculptor copy() {
        return new SoulSculptor(this);
    }
}

class SoulSculptorEffect extends ContinuousEffectImpl {

    public SoulSculptorEffect() {
        super(Duration.Custom, Outcome.LoseAbility);
        staticText = "target becomes an Enchantment and loses all abilites";
        dependencyTypes.add(DependencyType.EnchantmentAddingRemoving);
        dependencyTypes.add(DependencyType.AddingAbility);

    }

    public SoulSculptorEffect(final SoulSculptorEffect effect) {
        super(effect);
    }

    @Override
    public SoulSculptorEffect copy() {
        return new SoulSculptorEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent != null) {
            affectedObjectList.add(new MageObjectReference(targetPermanent, game));
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent == null) {
            this.discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.retainAllEnchantmentSubTypes(game);
                permanent.addCardType(game, CardType.ENCHANTMENT);
                break;
            case AbilityAddingRemovingEffects_6:
                if (sublayer == SubLayer.NA) {
                    permanent.getAbilities().clear();
                }
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return Layer.TypeChangingEffects_4 == layer
                || Layer.AbilityAddingRemovingEffects_6 == layer;
    }

}

enum SoulSculptorCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getStack().isEmpty()) {
            StackObject stackObject = game.getStack().getFirst();
            if (stackObject != null) {
                return !stackObject.getCardType(game).contains(CardType.CREATURE);
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "creature spell cast";
    }

}
