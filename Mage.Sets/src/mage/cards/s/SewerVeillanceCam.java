package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class SewerVeillanceCam extends CardImpl {

    public SewerVeillanceCam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this artifact enters or leaves the battlefield, you may tap or untap target creature.
        Ability ability = new EntersBattlefieldOrLeavesSourceTriggeredAbility(new MayTapOrUntapTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {3}{U}, Sacrifice this artifact: Draw two cards.
        Ability ability2 = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{3}{U}"));
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
    }

    private SewerVeillanceCam(final SewerVeillanceCam card) {
        super(card);
    }

    @Override
    public SewerVeillanceCam copy() {
        return new SewerVeillanceCam(this);
    }
}
