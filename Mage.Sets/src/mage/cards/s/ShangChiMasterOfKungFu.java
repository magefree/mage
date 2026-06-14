package mage.cards.s;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class ShangChiMasterOfKungFu extends CardImpl {

    public ShangChiMasterOfKungFu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You may activate abilities of creatures you control as though those creatures had haste.
        this.addAbility(new SimpleStaticAbility(new ShangChiMasterOfKungFuHasteEffect()));

        // {T}: Add two mana of any one color. Spend this mana only to activate abilities of creature sources.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 2, new ShangChiMasterOfKungFuManaBuilder(), true));
    }

    private ShangChiMasterOfKungFu(final ShangChiMasterOfKungFu card) {
        super(card);
    }

    @Override
    public ShangChiMasterOfKungFu copy() {
        return new ShangChiMasterOfKungFu(this);
    }
}

class ShangChiMasterOfKungFuHasteEffect extends AsThoughEffectImpl {

    ShangChiMasterOfKungFuHasteEffect() {
        super(AsThoughEffectType.ACTIVATE_HASTE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "you may activate abilities of creatures you control as though those creatures had haste";
    }

    private ShangChiMasterOfKungFuHasteEffect(final ShangChiMasterOfKungFuHasteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ShangChiMasterOfKungFuHasteEffect copy() {
        return new ShangChiMasterOfKungFuHasteEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId());
    }
}

class ShangChiMasterOfKungFuManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ShangChiMasterOfKungFuConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities of creature sources";
    }
}

class ShangChiMasterOfKungFuConditionalMana extends ConditionalMana {

    ShangChiMasterOfKungFuConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate abilities of creature sources";
        addCondition(new ShangChiMasterOfKungFuManaCondition());
    }
}

class ShangChiMasterOfKungFuManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return source != null
                && !source.isActivated()
                && source.isActivatedAbility()
                && object != null
                && object.isCreature(game);
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
