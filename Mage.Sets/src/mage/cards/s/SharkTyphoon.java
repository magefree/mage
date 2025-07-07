package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SharkToken;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SharkTyphoon extends CardImpl {

    public SharkTyphoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}");

        // Whenever you cast a noncreature spell, create an X/X blue Shark creature token with flying, where X is that spell's converted mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SharkTyphoonCastEffect(),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE,
                false, SetTargetPointer.SPELL
        ));

        // Cycling {X}{1}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{X}{1}{U}")));

        // When you cycle Shark Typhoon, create an X/X blue Shark creature token with flying.
        this.addAbility(new CycleTriggeredAbility(new SharkTyphoonCycleEffect()));
    }

    private SharkTyphoon(final SharkTyphoon card) {
        super(card);
    }

    @Override
    public SharkTyphoon copy() {
        return new SharkTyphoon(this);
    }
}

class SharkTyphoonCastEffect extends OneShotEffect {

    SharkTyphoonCastEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X blue Shark creature token with flying, where X is that spell's mana value";
    }

    private SharkTyphoonCastEffect(final SharkTyphoonCastEffect effect) {
        super(effect);
    }

    @Override
    public SharkTyphoonCastEffect copy() {
        return new SharkTyphoonCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        int xValue = 0;
        if (spell != null) {
            xValue = spell.getManaValue();
        }
        return new SharkToken(xValue).putOntoBattlefield(1, game, source, source.getControllerId());
    }
}

class SharkTyphoonCycleEffect extends OneShotEffect {

    SharkTyphoonCycleEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X blue Shark creature token with flying";
    }

    private SharkTyphoonCycleEffect(final SharkTyphoonCycleEffect effect) {
        super(effect);
    }

    @Override
    public SharkTyphoonCycleEffect copy() {
        return new SharkTyphoonCycleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object xValueObject = getValue("cycleXValue");
        int xValue = 0;
        if (xValueObject instanceof Integer) {
            xValue = (int) xValueObject;
        }
        return new SharkToken(xValue).putOntoBattlefield(1, game, source, source.getControllerId());
    }
}
