
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.WoodToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class JunglePatrol extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a token named Wood");

    static {
        filter.add(new NamePredicate("Wood"));
        filter.add(TokenPredicate.TRUE);
    }

    public JunglePatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}{G}, {T}: Create a 0/1 green Wall creature token with defender named Wood.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new WoodToken()), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Sacrifice a token named Wood: Add {R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.RedMana(1), new PermanentsOnBattlefieldCount(filter)),
                new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true))));
    }

    private JunglePatrol(final JunglePatrol card) {
        super(card);
    }

    @Override
    public JunglePatrol copy() {
        return new JunglePatrol(this);
    }
}
