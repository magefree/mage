package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.EldraziToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UlamogsDreadsire extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent with mana value 1 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 0));
    }

    public UlamogsDreadsire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward--Sacrifice a permanent with mana value 1 or greater.
        this.addAbility(new WardAbility(new SacrificeTargetCost(filter), false));

        // {T}: Create a 10/10 colorless Eldrazi creature token.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new EldraziToken()), new TapSourceCost()));
    }

    private UlamogsDreadsire(final UlamogsDreadsire card) {
        super(card);
    }

    @Override
    public UlamogsDreadsire copy() {
        return new UlamogsDreadsire(this);
    }
}
