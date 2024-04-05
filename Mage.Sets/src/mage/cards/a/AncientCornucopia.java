package mage.cards.a;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientCornucopia extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that's one or more colors");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public AncientCornucopia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        // Whenever you cast a spell that's one or more colors, you may gain 1 life for each of that spell's colors. Do this only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GainLifeEffect(AncientCornucopiaValue.instance)
                        .setText("gain 1 life for each of that spell's colors"), filter, true
        ).setDoOnlyOnceEachTurn(true));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private AncientCornucopia(final AncientCornucopia card) {
        super(card);
    }

    @Override
    public AncientCornucopia copy() {
        return new AncientCornucopia(this);
    }
}

enum AncientCornucopiaValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(effect.getValue("spellCast"))
                .map(Spell.class::cast)
                .map(spell -> spell.getColor(game))
                .map(ObjectColor::getColorCount)
                .orElse(0);
    }

    @Override
    public AncientCornucopiaValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "of that spell's colors";
    }

    @Override
    public String toString() {
        return "1";
    }
}
