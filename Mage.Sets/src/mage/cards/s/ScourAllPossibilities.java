package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScourAllPossibilities extends CardImpl {

    public ScourAllPossibilities(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Scry 2, then draw a card.
        this.getSpellAbility().addEffect(new ScryEffect(2).setText("scry 2,"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("then"));

        // Flashback {4}{U}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{4}{U}"), TimingRule.SORCERY));

    }

    private ScourAllPossibilities(final ScourAllPossibilities card) {
        super(card);
    }

    @Override
    public ScourAllPossibilities copy() {
        return new ScourAllPossibilities(this);
    }
}
