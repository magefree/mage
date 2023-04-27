package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DiscardCardPlayerTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TelekineticBonds extends CardImpl {

    public TelekineticBonds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}{U}");

        // Whenever a player discards a card, you may pay {1}{U}. If you do, you may tap or untap target permanent.
        Ability ability = new DiscardCardPlayerTriggeredAbility(new DoIfCostPaid(new MayTapOrUntapTargetEffect(), new ManaCostsImpl<>("{1}{U}")), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    private TelekineticBonds(final TelekineticBonds card) {
        super(card);
    }

    @Override
    public TelekineticBonds copy() {
        return new TelekineticBonds(this);
    }
}