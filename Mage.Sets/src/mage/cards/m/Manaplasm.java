package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class Manaplasm extends CardImpl {

    public Manaplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell, Manaplasm gets +X/+X until end of turn, where X is that spell's converted mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(
                ManaplasmValue.instance, ManaplasmValue.instance,
                Duration.EndOfTurn, true
        ), false));
    }

    private Manaplasm(final Manaplasm card) {
        super(card);
    }

    @Override
    public Manaplasm copy() {
        return new Manaplasm(this);
    }
}

enum ManaplasmValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional.of((Spell) effect.getValue("spellCast")).map(Spell::getManaValue).orElse(0);
    }

    @Override
    public ManaplasmValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that spell's mana value";
    }

    @Override
    public String toString() {
        return "X";
    }
}
