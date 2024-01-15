
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CelestialKirin extends CardImpl {

    public CelestialKirin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KIRIN);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a Spirit or Arcane spell, destroy all permanents with that spell's converted mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CelestialKirinEffect(), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD,
                false, SetTargetPointer.SPELL
        ));
    }

    private CelestialKirin(final CelestialKirin card) {
        super(card);
    }

    @Override
    public CelestialKirin copy() {
        return new CelestialKirin(this);
    }
}

class CelestialKirinEffect extends OneShotEffect {

    CelestialKirinEffect() {
        super(Outcome.GainLife);
        this.staticText = "destroy all permanents with that spell's mana value";
    }

    private CelestialKirinEffect(final CelestialKirinEffect effect) {
        super(effect);
    }

    @Override
    public CelestialKirinEffect copy() {
        return new CelestialKirinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            int cmc = spell.getManaValue();
            FilterPermanent filter = new FilterPermanent();
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, cmc));
            return new DestroyAllEffect(filter).apply(game, source);
        }
        return false;
    }
}
