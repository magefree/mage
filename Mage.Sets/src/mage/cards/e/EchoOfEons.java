package mage.cards.e;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EchoOfEons extends CardImpl {

    public EchoOfEons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Each player shuffles their hand and graveyard into their library, then draws seven cards.
        this.getSpellAbility().addEffect(new ShuffleHandGraveyardAllEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);

        // Flashback {2}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{2}{U}")));
    }

    private EchoOfEons(final EchoOfEons card) {
        super(card);
    }

    @Override
    public EchoOfEons copy() {
        return new EchoOfEons(this);
    }
}
