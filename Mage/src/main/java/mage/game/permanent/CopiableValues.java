package mage.game.permanent;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.constants.CardType;
import mage.game.Game;
import mage.util.SubTypes;

import java.util.List;

/**
 * @author TheElk801
 */
public class CopiableValues extends PermanentImpl {

    CopiableValues() {
        super(null, null, "");
    }

    private CopiableValues(final CopiableValues permanent) {
        super(permanent);
    }

    @Override
    public MageObject getBasicMageObject(Game game) {
        return this;
    }

    @Override
    public CopiableValues copy() {
        return new CopiableValues(this);
    }

    void copyFrom(Permanent permanent, Game game) {
        this.name = "" + permanent.getName();
        this.manaCost = permanent.getManaCost().copy();
        this.color = permanent.getColor(game).copy();
        this.cardType.clear();
        this.cardType.addAll(permanent.getCardType(game));
        this.subtype.copyFrom(permanent.getSubtype(game));
        this.supertype.clear();
        this.supertype.addAll(permanent.getSuperType());
        this.abilities = permanent.getAbilities(game).copy();
        this.power = permanent.getPower().copy();
        this.toughness = permanent.getToughness().copy();
        this.startingLoyalty = permanent.getStartingLoyalty();
        this.startingDefense = permanent.getStartingDefense();
        this.expansionSetCode = permanent.getExpansionSetCode();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return manaCost;
    }

    @Override
    public List<CardType> getCardType(Game game) {
        return cardType;
    }

    @Override
    public SubTypes getSubtype(Game game) {
        return subtype;
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return super.getAbilities(null);
    }
}
