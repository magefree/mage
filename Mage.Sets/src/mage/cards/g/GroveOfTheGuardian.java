
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author LevelX2
 */
public final class GroveOfTheGuardian extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public GroveOfTheGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1), new TapSourceCost()));

        // {3}{G}{W}, {T}, Tap two untapped creatures you control, Sacrifice Grove of the Guardian: Create an 8/8 green and white Elemental creature token with vigilance.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ElementalToken(), 1), new ManaCostsImpl("{3}{G}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, false)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public GroveOfTheGuardian(final GroveOfTheGuardian card) {
        super(card);
    }

    @Override
    public GroveOfTheGuardian copy() {
        return new GroveOfTheGuardian(this);
    }

    private static class ElementalToken extends TokenImpl {

        ElementalToken() {
            super("Elemental", "8/8 green and white Elemental creature token with vigilance");

            cardType.add(CardType.CREATURE);
            color.setGreen(true);
            color.setWhite(true);
            this.subtype.add(SubType.ELEMENTAL);
            power = new MageInt(8);
            toughness = new MageInt(8);
            this.addAbility(VigilanceAbility.getInstance());
        }
        public ElementalToken(final ElementalToken token) {
            super(token);
        }

        public ElementalToken copy() {
            return new ElementalToken(this);
        }
    }
}
