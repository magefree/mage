package mage.cards.c;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.abilities.mana.conditional.PlaneswalkerCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.Filter;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandrasEmbercat extends CardImpl {

    public ChandrasEmbercat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add {R}. Spend this mana only to cast an Elemental spell or a Chandra planeswalker spell.
        this.addAbility(new ConditionalColoredManaAbility(
                new TapSourceCost(), Mana.RedMana(1),
                new ChandrasEmbercatManaBuilder()
        ));
    }

    private ChandrasEmbercat(final ChandrasEmbercat card) {
        super(card);
    }

    @Override
    public ChandrasEmbercat copy() {
        return new ChandrasEmbercat(this);
    }
}

class ChandrasEmbercatManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ChandrasEmbercatConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an Elemental spell or a Chandra planeswalker spell.";
    }
}

class ChandrasEmbercatElementalManaCondition extends CreatureCastManaCondition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (!super.apply(game, source)) {
            return false;
        }
        MageObject object = game.getObject(source.getSourceId());
        if (object == null) {
            return false;
        }
        return object.hasSubtype(SubType.ELEMENTAL, game);
    }
}

class ChandrasEmbercatPlaneswalkerManaCondition extends PlaneswalkerCastManaCondition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (!super.apply(game, source)) {
            return false;
        }
        MageObject object = game.getObject(source.getSourceId());
        if (object == null) {
            return false;
        }
        return object.hasSubtype(SubType.CHANDRA, game);
    }
}

class ChandrasEmbercatConditionalMana extends ConditionalMana {

    ChandrasEmbercatConditionalMana(Mana mana) {
        super(mana);
        setComparisonScope(Filter.ComparisonScope.Any);
        addCondition(new ChandrasEmbercatElementalManaCondition());
        addCondition(new ChandrasEmbercatPlaneswalkerManaCondition());
    }
}
