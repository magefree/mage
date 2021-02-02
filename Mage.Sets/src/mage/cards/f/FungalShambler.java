
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class FungalShambler extends CardImpl {

    public FungalShambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{G}{U}");
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Fungal Shambler deals damage to an opponent, you draw a card and that opponent discards a card.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("you draw a card");
        Ability ability = new DealsDamageToOpponentTriggeredAbility(effect, false, false, true);
        effect = new DiscardTargetEffect(1);
        effect.setText("and that opponent discards a card");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private FungalShambler(final FungalShambler card) {
        super(card);
    }

    @Override
    public FungalShambler copy() {
        return new FungalShambler(this);
    }
}
