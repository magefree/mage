

package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public final class CeruleanWisps extends CardImpl {

    public CeruleanWisps (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Target creature becomes blue until end of turn. Untap that creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(ObjectColor.BLUE, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public CeruleanWisps (final CeruleanWisps card) {
        super(card);
    }

    @Override
    public CeruleanWisps copy() {
        return new CeruleanWisps(this);
    }

}

