
package mage.cards.p;

import java.util.Objects;
import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
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
 * @author TheElk801
 */
public final class PillarOfOrigins extends CardImpl {

    public PillarOfOrigins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // As Pillar of Origins enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell if the chosen type.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new PillarOfOriginsManaBuilder(), true));
    }

    private PillarOfOrigins(final PillarOfOrigins card) {
        super(card);
    }

    @Override
    public PillarOfOrigins copy() {
        return new PillarOfOrigins(this);
    }
}

class PillarOfOriginsManaBuilder extends ConditionalManaBuilder {

    SubType creatureType;

    @Override
    public ConditionalManaBuilder setMana(Mana mana, Ability source, Game game) {
        creatureType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
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
        return new PillarOfOriginsConditionalMana(this.mana, creatureType);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell of the chosen type";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.creatureType);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        return this.creatureType == ((PillarOfOriginsManaBuilder) obj).creatureType;
    }

}

class PillarOfOriginsConditionalMana extends ConditionalMana {

    public PillarOfOriginsConditionalMana(Mana mana, SubType creatureType) {
        super(mana);
        staticText = "Spend this mana only to cast a creature spell of the chosen type";
        addCondition(new PillarOfOriginsManaCondition(creatureType));
    }
}

class PillarOfOriginsManaCondition extends CreatureCastManaCondition {

    SubType creatureType;

    PillarOfOriginsManaCondition(SubType creatureType) {
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
