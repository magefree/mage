
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class WitnessTheEnd extends CardImpl {

    public WitnessTheEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Target opponent exiles two cards from their hand and loses 2 life.
        getSpellAbility().addEffect(new ExileFromZoneTargetEffect(Zone.HAND, null, "", new FilterCard("cards"), 2));
        Effect effect = new LoseLifeTargetEffect(2);
        effect.setText("and loses 2 life");
        getSpellAbility().addTarget(new TargetOpponent());
        getSpellAbility().addEffect(effect);
    }

    public WitnessTheEnd(final WitnessTheEnd card) {
        super(card);
    }

    @Override
    public WitnessTheEnd copy() {
        return new WitnessTheEnd(this);
    }
}
