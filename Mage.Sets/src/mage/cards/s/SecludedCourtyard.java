/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.s;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author jeffwadsworth
 */
public final class SecludedCourtyard extends CardImpl {

    public SecludedCourtyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As Secluded Courtyard enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Add one mana of any color. Spend this mana only to cast a creature spell of the chosen type or activate an ability of a creature or creature card of the chosen type. 
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new SecludedCourtyardManaBuilder(), true));
    }

    private SecludedCourtyard(final SecludedCourtyard card) {
        super(card);
    }

    @Override
    public SecludedCourtyard copy() {
        return new SecludedCourtyard(this);
    }
}

class SecludedCourtyardManaBuilder extends ConditionalManaBuilder {

    SubType creatureType;

    @Override
    public ConditionalManaBuilder setMana(Mana mana, Ability source, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (subType != null) {
            creatureType = subType;
        }
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null
                && sourceObject != null
                && mana.getAny() == 0) {
            game.informPlayers(controller.getLogName() + " produces " + mana.toString() + " with " + sourceObject.getLogName()
                    + " (can only be spent to cast creatures of type " + creatureType + " and activate an ability of a creature or creature card of the chosen type)");
        }
        return super.setMana(mana, source, game);
    }

    @Override
    public ConditionalMana build(Object... options) {
        return new SecludedCourtyardConditionalMana(this.mana, creatureType);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell of the chosen type or activate an ability of a creature or creature card of the chosen type";
    }
}

class SecludedCourtyardConditionalMana extends ConditionalMana {

    SubType creatureType;

    public SecludedCourtyardConditionalMana(Mana mana, SubType creatureType) {
        super(mana);
        staticText = "Spend this mana only to cast a creature spell of the chosen type or activate an ability of a creature or creature card of the chosen type";
        addCondition(new SecludedCourtyardManaCondition(creatureType));
    }
}

class SecludedCourtyardManaCondition implements Condition {

    SubType creatureType;

    SecludedCourtyardManaCondition(SubType creatureType) {
        this.creatureType = creatureType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        // casting a creature card or using its ability
        if (creatureType != null
                && object != null
                && object.hasSubtype(creatureType, game)) {
            return true;
        }
        return false;
    }
}
