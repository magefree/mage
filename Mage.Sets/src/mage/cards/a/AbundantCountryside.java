package mage.cards.a;

import java.util.UUID;

import mage.ConditionalMana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastConditionalMana;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.HumanToken;
import mage.game.permanent.token.ShapeshifterColorlessToken;

/**
 *
 * @author muz
 */
public final class AbundantCountryside extends CardImpl {

    public AbundantCountryside(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new AbundantCountrysideManaBuilder()));

        // {6}, {T}: Create a 1/1 colorless Shapeshifter creature token with changeling.
        Ability ability = new SimpleActivatedAbility(
            new CreateTokenEffect(new ShapeshifterColorlessToken()), new ManaCostsImpl<>("{6}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private AbundantCountryside(final AbundantCountryside card) {
        super(card);
    }

    @Override
    public AbundantCountryside copy() {
        return new AbundantCountryside(this);
    }
}

class AbundantCountrysideManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new CreatureCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell";
    }
}
