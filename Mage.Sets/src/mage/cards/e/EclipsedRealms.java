package mage.cards.e;

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

import java.util.Objects;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class EclipsedRealms extends CardImpl {

    public EclipsedRealms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As this land enters, choose Elemental, Elf, Faerie, Giant, Goblin, Kithkin, Merfolk, or Treefolk.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(
                Outcome.Benefit, SubType.ELEMENTAL, SubType.ELF, SubType.FAERIE, SubType.GIANT,
                SubType.GOBLIN, SubType.KITHKIN, SubType.MERFOLK, SubType.TREEFOLK
        )));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a spell of the chosen type or activate an ability of a source of the chosen type.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new EclipsedRealmsManaBuilder(), true));

    }

    private EclipsedRealms(final EclipsedRealms card) {
        super(card);
    }

    @Override
    public EclipsedRealms copy() {
        return new EclipsedRealms(this);
    }
}

class EclipsedRealmsManaBuilder extends ConditionalManaBuilder {

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
            game.informPlayers(controller.getLogName() + " produces " + mana.toString()
                    + " with " + sourceObject.getLogName()
                    + " (can only be spent to cast spells of type " + creatureType
                    + " or activate abilities of sources of that type)");
        }
        return super.setMana(mana, source, game);
    }

    @Override
    public ConditionalMana build(Object... options) {
        return new EclipsedRealmsConditionalMana(this.mana, creatureType);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a spell of the chosen type or activate an ability of a source of the chosen type";
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
        return this.creatureType == ((EclipsedRealmsManaBuilder) obj).creatureType;
    }
}

class EclipsedRealmsConditionalMana extends ConditionalMana {

    EclipsedRealmsConditionalMana(Mana mana, SubType creatureType) {
        super(mana);
        staticText = "Spend this mana only to cast a spell of the chosen type or activate an ability of a source of the chosen type";
        addCondition(new EclipsedRealmsManaCondition(creatureType));
    }
}

class EclipsedRealmsManaCondition implements Condition {

    SubType creatureType;

    EclipsedRealmsManaCondition(SubType creatureType) {
        this.creatureType = creatureType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.hasSubtype(creatureType, game);
    }
}
