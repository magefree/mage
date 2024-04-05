package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FreestriderCommando extends CardImpl {

    public FreestriderCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Freestrider Commando enters the battlefield with two +1/+1 counters on it if it wasn't cast or no mana was spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                        FreestriderCommandoCondition.instance, ""
                ), "with two +1/+1 counters on it if it wasn't cast or no mana was spent to cast it"
        ));

        // Plot {3}{G}
        this.addAbility(new PlotAbility("{3}{G}"));
    }

    private FreestriderCommando(final FreestriderCommando card) {
        super(card);
    }

    @Override
    public FreestriderCommando copy() {
        return new FreestriderCommando(this);
    }
}

enum FreestriderCommandoCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        // Check if the spell exists on the stack
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (spell == null) {
            return true; // cannot find the spell, so it wasn't cast.
        }
        // spell was found, did it cost mana?
        return 0 == spell.getStackAbility().getManaCostsToPay().getUsedManaToPay().count();
    }

    @Override
    public String toString() {
        return "if it wasn't cast or no mana was spent to cast it";
    }
}
