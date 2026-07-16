package mage.cards.a;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;

/**
 *
 * @author muz
 */
public final class AvengersTower extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.HERO, "Hero");

    public AvengersTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a Hero spell or to activate an ability of a Hero source.
        this.addAbility(new ConditionalAnyColorManaAbility(
            new TapSourceCost(), 1, new AvengersTowerManaBuilder()
        ));

        // {4}, {T}: Look at the top three cards of your library. You may reveal a Hero card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        Ability ability =new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
            3, 1, filter, PutCards.HAND, PutCards.BOTTOM_ANY
        ), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AvengersTower(final AvengersTower card) {
        super(card);
    }

    @Override
    public AvengersTower copy() {
        return new AvengersTower(this);
    }
}

class AvengersTowerManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new AvengersTowerConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a Hero spell or activate an ability of a Hero source";
    }
}

class AvengersTowerConditionalMana extends ConditionalMana {
    public AvengersTowerConditionalMana(Mana mana) {
        super(mana);
        addCondition(AvengersTowerCondition.instance);
    }
}

enum AvengersTowerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.hasSubtype(SubType.HERO, game);
    }
}
