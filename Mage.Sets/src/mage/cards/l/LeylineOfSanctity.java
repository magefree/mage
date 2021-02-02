

package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterStackObject;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class LeylineOfSanctity extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("spells or abilities your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public LeylineOfSanctity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");


        // If Leyline of Sanctity is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // You have hexproof. (You can't be the target of spells or abilities your opponents control.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControllerEffect(HexproofAbility.getInstance())));
    }

    private LeylineOfSanctity(final LeylineOfSanctity card) {
        super(card);
    }

    @Override
    public LeylineOfSanctity copy() {
        return new LeylineOfSanctity(this);
    }

}

