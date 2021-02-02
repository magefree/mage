
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class BanishingKnack extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    public BanishingKnack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new TapSourceCost());
        gainedAbility.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(gainedAbility, Duration.EndOfTurn)
                .setText("Until end of turn, target creature gains \"{T}: Return target nonland permanent to its owner's hand.\"")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BanishingKnack(final BanishingKnack card) {
        super(card);
    }

    @Override
    public BanishingKnack copy() {
        return new BanishingKnack(this);
    }
}
