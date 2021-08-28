
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.HomunculusToken;

/**
 *
 * @author Plopman
 */
public final class PuppetConjurer extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Homunculus");

    static {
        filter.add(SubType.HOMUNCULUS.getPredicate());
    }

    public PuppetConjurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {U}, {tap}: Create a 0/1 blue Homunculus artifact creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new HomunculusToken()), new ManaCostsImpl("{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // At the beginning of your upkeep, sacrifice a Homunculus.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeEffect(filter, 1, ""), TargetController.YOU, false));
    }

    private PuppetConjurer(final PuppetConjurer card) {
        super(card);
    }

    @Override
    public PuppetConjurer copy() {
        return new PuppetConjurer(this);
    }
}
