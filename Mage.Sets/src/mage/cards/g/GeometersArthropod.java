package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.VariableManaCostPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class GeometersArthropod extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with {X} in its mana cost");

    static {
        filter.add(VariableManaCostPredicate.instance);
    }

    public GeometersArthropod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.FRACTAL);
        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you cast a spell with {X} in its mana cost, look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GeometersArthropodEffect(), filter, false));
    }

    private GeometersArthropod(final GeometersArthropod card) {
        super(card);
    }

    @Override
    public GeometersArthropod copy() {
        return new GeometersArthropod(this);
    }
}

class GeometersArthropodEffect extends OneShotEffect {
    GeometersArthropodEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top X cards of your library. "
          + "Put one of them into your hand and the rest on the bottom "
          + "of your library in a random order.";
    }

    private GeometersArthropodEffect(final GeometersArthropodEffect effect) {
        super(effect);
    }

    @Override
    public GeometersArthropodEffect copy() {
        return new GeometersArthropodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }

        int xValue = CardUtil.getSourceCostsTag(game, spell.getSpellAbility(), "X", 0);
        if (xValue < 1) {
            return true;
        }

        return new LookLibraryAndPickControllerEffect(
            StaticValue.get(xValue), 1, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ).apply(game, source);
    }
}
