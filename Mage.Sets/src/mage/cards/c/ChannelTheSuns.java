
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author magenoxx
 */
public final class ChannelTheSuns extends CardImpl {

    public ChannelTheSuns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Add {W}{U}{B}{R}{G}.
        Effect effect = new AddManaToManaPoolSourceControllerEffect(new Mana(1, 1, 1, 1, 1, 0, 0, 0));
        this.getSpellAbility().addEffect(effect);
    }

    private ChannelTheSuns(final ChannelTheSuns card) {
        super(card);
    }

    @Override
    public ChannelTheSuns copy() {
        return new ChannelTheSuns(this);
    }
}
