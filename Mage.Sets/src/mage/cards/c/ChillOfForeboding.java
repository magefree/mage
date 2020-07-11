package mage.cards.c;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveEachPlayerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.TimingRule;

import java.util.UUID;

/**
 * @author North
 */
public final class ChillOfForeboding extends CardImpl {

    public ChillOfForeboding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Each player puts the top five cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new PutTopCardOfLibraryIntoGraveEachPlayerEffect(5, TargetController.ANY));

        // Flashback {7}{U}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{7}{U}"), TimingRule.SORCERY));
    }

    private ChillOfForeboding(final ChillOfForeboding card) {
        super(card);
    }

    @Override
    public ChillOfForeboding copy() {
        return new ChillOfForeboding(this);
    }
}
