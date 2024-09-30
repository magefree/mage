package mage.cards.t;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.condition.Condition;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 *
 * @author DominionSpy
 */
public final class TinStreetGossip extends CardImpl {

    public TinStreetGossip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Add {R}{G}. Spend this mana only to cast face-down spells or to turn creatures face up.
        this.addAbility(new ConditionalColoredManaAbility(
                new Mana(0, 0, 0, 1, 1, 0, 0, 0),
                new TinStreetGossipManaBuilder()));
    }

    private TinStreetGossip(final TinStreetGossip card) {
        super(card);
    }

    @Override
    public TinStreetGossip copy() {
        return new TinStreetGossip(this);
    }
}

class TinStreetGossipManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new TinStreetGossipConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast face-down spells or to turn creatures face up.";
    }
}

class TinStreetGossipConditionalMana extends ConditionalMana {

    TinStreetGossipConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast face-down spells or to turn creatures face up.";
        addCondition(new TinStreetGossipManaCondition());
    }
}

class TinStreetGossipManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object instanceof Spell) {
            return ((Spell) object).isFaceDown(game);
        }
        return source instanceof TurnFaceUpAbility;
    }
}
