package mage.cards.b;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.FreerunningAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.Filter;
import mage.game.Game;

import java.util.UUID;

public class BrotherhoodHeadquarters extends CardImpl {
    public BrotherhoodHeadquarters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Add one colorless
        this.addAbility(new ColorlessManaAbility());

        // Add one mana of any color. Spend this mana only to cast an Assassin spell or a spell that has freerunning, or to activate an ability of an Assassin source.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new BrotherhoodHeadquartersManaBuilder()));
    }

    public BrotherhoodHeadquarters(BrotherhoodHeadquarters card) {
        super(card);
    }

    @Override
    public BrotherhoodHeadquarters copy() {
        return new BrotherhoodHeadquarters(this);
    }

    class BrotherhoodHeadquartersManaBuilder extends ConditionalManaBuilder {

        @Override
        public String getRule() {
            return "Spend this mana only to cast an Assassin spell or a spell that has freerunning, or to activate an ability of an Assassin source.";
        }

        @Override
        public ConditionalMana build(Object... options) {
            return new BrotherhoodHeadquartersConditionalMana(this.mana);
        }
    }

    class BrotherhoodHeadquartersConditionalMana extends ConditionalMana {

        public BrotherhoodHeadquartersConditionalMana(Mana mana) {
            super(mana);
            addCondition(new BrotherhoodHeadquartersCreatureManaCondition());
            addCondition(new BrotherhoodHeadquartersFreerunningManaCondition());
            addCondition(new BrotherhoodHeadquartersAssassinSourceManaCondition());
            setComparisonScope(Filter.ComparisonScope.Any);
        }
    }

    class BrotherhoodHeadquartersCreatureManaCondition extends CreatureCastManaCondition {

        @Override
        public boolean apply(Game game, Ability source) {
            if (super.apply(game, source)) {
                MageObject object = game.getObject(source.getSourceId());
                return object != null && object.hasSubtype(SubType.ASSASSIN, game);
            }

            return false;
        }
    }

    class BrotherhoodHeadquartersFreerunningManaCondition implements Condition {

        @Override
        public boolean apply(Game game, Ability source) {
            return source instanceof FreerunningAbility;
        }
    }

    class BrotherhoodHeadquartersAssassinSourceManaCondition implements Condition {

        @Override
        public boolean apply(Game game, Ability source) {
            if (source.isActivatedAbility()) {
                MageObject object = game.getObject(source.getSourceId());
                return object != null && object.hasSubtype(SubType.ASSASSIN, game);
            }

            return false;
        }
    }
}
