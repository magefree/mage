
package mage.cards.p;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TGower
 */
public final class PrimalBeyond extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Elemental card from your hand");

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
    }

    public PrimalBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Primal Beyond enters the battlefield, you may reveal an Elemental card from your hand. If you don't, Primal Beyond enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal an Elemental card from your hand. If you don't, {this} enters the battlefield tapped"));
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add one mana of any color. Spend this mana only to cast an Elemental spell or activate an ability of an Elemental.
        Ability ability = new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new PrimalBeyondManaBuilder(), true);
        this.addAbility(ability);
    }

    private PrimalBeyond(final PrimalBeyond card) {
        super(card);
    }

    @Override
    public PrimalBeyond copy() {
        return new PrimalBeyond(this);
    }
}

class PrimalBeyondManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new PrimalBeyondConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an Elemental spell or activate an ability of an Elemental";
    }
}

class PrimalBeyondConditionalMana extends ConditionalMana {

    public PrimalBeyondConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast an Elemental spell or activate an ability of an Elemental";
        addCondition(new PrimalBeyondManaCondition());
    }
}

class PrimalBeyondManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        return object != null && object.hasSubtype(SubType.ELEMENTAL, game);
    }
}
