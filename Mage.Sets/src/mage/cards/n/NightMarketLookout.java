
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class NightMarketLookout extends CardImpl {

    public NightMarketLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Night Market Lookout becomes tapped, each opponent loses 1 life and you gain 1 life.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new LoseLifeOpponentsEffect(1), false);
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private NightMarketLookout(final NightMarketLookout card) {
        super(card);
    }

    @Override
    public NightMarketLookout copy() {
        return new NightMarketLookout(this);
    }
}
