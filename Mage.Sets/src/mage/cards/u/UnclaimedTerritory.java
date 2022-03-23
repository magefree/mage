
package mage.cards.u;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class UnclaimedTerritory extends CardImpl {

    public UnclaimedTerritory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As Unclaimed Territory enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell of the chosen type.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new UnclaimedTerritoryManaBuilder(), true));
    }

    private UnclaimedTerritory(final UnclaimedTerritory card) {
        super(card);
    }

    @Override
    public UnclaimedTerritory copy() {
        return new UnclaimedTerritory(this);
    }
}

class UnclaimedTerritoryManaBuilder extends ConditionalManaBuilder {

    SubType creatureType;

    @Override
    public ConditionalManaBuilder setMana(Mana mana, Ability source, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (subType != null) {
            creatureType = subType;
        }
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null && mana.getAny() == 0) {
            game.informPlayers(controller.getLogName() + " produces " + mana.toString() + " with " + sourceObject.getLogName()
                    + " (can only be spent to cast creatures of type " + creatureType + ")");
        }
        return super.setMana(mana, source, game);
    }

    @Override
    public ConditionalMana build(Object... options) {
        return new UnclaimedTerritoryConditionalMana(this.mana, creatureType);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell of the chosen type";
    }
}

class UnclaimedTerritoryConditionalMana extends ConditionalMana {

    public UnclaimedTerritoryConditionalMana(Mana mana, SubType creatureType) {
        super(mana);
        staticText = "Spend this mana only to cast a creature spell of the chosen type";
        addCondition(new UnclaimedTerritoryManaCondition(creatureType));
    }
}

class UnclaimedTerritoryManaCondition extends CreatureCastManaCondition {

    SubType creatureType;

    UnclaimedTerritoryManaCondition(SubType creatureType) {
        this.creatureType = creatureType;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        // check: ... to cast a creature spell
        if (super.apply(game, source)) {
            // check: ... of the chosen type
            MageObject object = game.getObject(source);
            if (creatureType != null && object != null && object.hasSubtype(creatureType, game)) {
                return true;
            }
        }
        return false;
    }
}
