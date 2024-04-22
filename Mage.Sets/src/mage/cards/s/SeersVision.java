package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.PlayWithHandRevealedEffect;
import mage.abilities.effects.common.discard.LookTargetHandChooseDiscardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author LoneFox
 */
public final class SeersVision extends CardImpl {

    public SeersVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{B}");

        // Your opponents play with their hands revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithHandRevealedEffect(TargetController.OPPONENT)));
        // Sacrifice Seer's Vision: Look at target player's hand and choose a card from it. That player discards that card. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new LookTargetHandChooseDiscardEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SeersVision(final SeersVision card) {
        super(card);
    }

    @Override
    public SeersVision copy() {
        return new SeersVision(this);
    }
}
