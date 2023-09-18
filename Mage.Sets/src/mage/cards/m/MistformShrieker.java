
package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesChosenCreatureTypeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class MistformShrieker extends CardImpl {

    public MistformShrieker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}: Mistform Shrieker becomes the creature type of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesChosenCreatureTypeSourceEffect(), new GenericManaCost(1)));

        // Morph {3}{U}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{U}{U}")));

    }

    private MistformShrieker(final MistformShrieker card) {
        super(card);
    }

    @Override
    public MistformShrieker copy() {
        return new MistformShrieker(this);
    }
}
