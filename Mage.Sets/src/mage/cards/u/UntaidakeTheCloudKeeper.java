
package mage.cards.u;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.game.Game;

/**
 *
 * @author anonymous
 */
public final class UntaidakeTheCloudKeeper extends CardImpl {

    public UntaidakeTheCloudKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.addSuperType(SuperType.LEGENDARY);

        // Untaidake, the Cloud Keeper enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}, Pay 2 life: Add {C}{C}. Spend this mana only to cast legendary spells.
        Ability ability = new ConditionalColorlessManaAbility(new TapSourceCost(), 2, new LegendarySpellManaBuilder());
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);

    }

    private UntaidakeTheCloudKeeper(final UntaidakeTheCloudKeeper card) {
        super(card);
    }

    @Override
    public UntaidakeTheCloudKeeper copy() {
        return new UntaidakeTheCloudKeeper(this);
    }
}

class LegendarySpellManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new LegendaryCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast legendary spells";
    }
}

class LegendaryCastConditionalMana extends ConditionalMana {

    public LegendaryCastConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast legendary spells";
        addCondition(new LegendaryCastManaCondition());
    }
}

class LegendaryCastManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            if (object != null && object.isLegendary()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
