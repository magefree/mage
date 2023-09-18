
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.DroidToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Styxo
 */
public final class NuteGunray extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("non-token artifact");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public NuteGunray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NEIMOIDIAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever an artifact enters the battlefield under your control, you may pay {1}. If you do, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new GenericManaCost(1)), new FilterArtifactPermanent()));

        // {1}{T}, Sacrifice a non-token artifact: Create a 1/1 colorless Battle Droid artifact creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new DroidToken()), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, false)));
        this.addAbility(ability);
    }

    private NuteGunray(final NuteGunray card) {
        super(card);
    }

    @Override
    public NuteGunray copy() {
        return new NuteGunray(this);
    }
}
