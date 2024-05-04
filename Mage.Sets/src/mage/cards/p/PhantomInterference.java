package mage.cards.p;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Spirit22Token;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PhantomInterference extends CardImpl {

    public PhantomInterference(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {3} -- Create a 2/2 white Spirit creature token with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Spirit22Token()));
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(3));

        // + {1} -- Counter target spell unless its controller pays {2}.
        this.getSpellAbility().addMode(new Mode(new CounterUnlessPaysEffect(new GenericManaCost(2)))
                .addTarget(new TargetSpell())
                .withCost(new GenericManaCost(1)));
    }

    private PhantomInterference(final PhantomInterference card) {
        super(card);
    }

    @Override
    public PhantomInterference copy() {
        return new PhantomInterference(this);
    }
}
