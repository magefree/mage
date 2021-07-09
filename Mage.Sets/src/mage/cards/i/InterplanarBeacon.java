package mage.cards.i;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import java.util.UUID;
import mage.abilities.mana.conditional.ConditionalAddManaOfTwoDifferentColorsAbility;

/**
 * @author TheElk801
 */
public final class InterplanarBeacon extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a planeswalker spell");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
    }

    public InterplanarBeacon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Whenever you cast a planeswalker spell, you gain 1 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GainLifeEffect(1), filter, false
        ));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add two mana of different colors. Spend this mana only to cast planeswalker spells.
        Ability ability = new ConditionalAddManaOfTwoDifferentColorsAbility(
                new GenericManaCost(1),
                new InterplanarBeaconManaBuilder()
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private InterplanarBeacon(final InterplanarBeacon card) {
        super(card);
    }

    @Override
    public InterplanarBeacon copy() {
        return new InterplanarBeacon(this);
    }
}

class InterplanarBeaconManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new InterplanarBeaconConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast planeswalker spells";
    }
}

class InterplanarBeaconConditionalMana extends ConditionalMana {

    InterplanarBeaconConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast planeswalker spells";
        this.addCondition(new InterplanarBeaconManaCondition());
    }
}

class InterplanarBeaconManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = source.getSourceObject(game);
        return object instanceof Spell
                && object.isPlaneswalker(game);
    }
}
