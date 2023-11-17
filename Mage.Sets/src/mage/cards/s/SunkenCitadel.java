package mage.cards.s;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.mana.AddConditionalManaChosenColorEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SunkenCitadel extends CardImpl {

    public SunkenCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);

        // Sunken Citadel enters the battlefield tapped. As it enters, choose a color.
        this.addAbility(new SunkenCitadelEntersBattlefieldAbility());

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));

        // {T}: Add two mana of the chosen color. Spend this mana only to activate abilities of land sources.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddConditionalManaChosenColorEffect(2, new SunkenCitadelManaBuilder()), new TapSourceCost()));
    }

    private SunkenCitadel(final SunkenCitadel card) {
        super(card);
    }

    @Override
    public SunkenCitadel copy() {
        return new SunkenCitadel(this);
    }
}

class SunkenCitadelEntersBattlefieldAbility extends StaticAbility {

    public SunkenCitadelEntersBattlefieldAbility() {
        super(Zone.ALL, new EntersBattlefieldEffect(new TapSourceEffect(true)));
        this.addEffect(new ChooseColorEffect(Outcome.Benefit));
    }

    private SunkenCitadelEntersBattlefieldAbility(final SunkenCitadelEntersBattlefieldAbility ability) {
        super(ability);
    }

    @Override
    public SunkenCitadelEntersBattlefieldAbility copy() {
        return new SunkenCitadelEntersBattlefieldAbility(this);
    }

    @Override
    public void addEffect(Effect effect) {
        if (!getEffects().isEmpty()) {
            Effect entersEffect = this.getEffects().get(0);
            if (entersEffect instanceof EntersBattlefieldEffect) {
                ((EntersBattlefieldEffect) entersEffect).addEffect(effect);
                return;
            }
        }
        super.addEffect(effect);
    }

    @Override
    public String getRule() {
        return "{this} enters the battlefield tapped. As it enters, choose a color.";
    }
}

class SunkenCitadelManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new SunkenCitadelConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities of land sources";
    }
}

class SunkenCitadelConditionalMana extends ConditionalMana {

    public SunkenCitadelConditionalMana(Mana mana) {
        super(mana);
        addCondition(SunkenCitadelCondition.instance);
    }
}

enum SunkenCitadelCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isLand(game);
    }
}
