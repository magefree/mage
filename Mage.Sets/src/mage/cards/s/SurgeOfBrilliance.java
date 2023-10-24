package mage.cards.s;

import mage.abilities.dynamicvalue.common.SpellsCastNotFromHandValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.watchers.common.SpellsCastNotFromHandWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurgeOfBrilliance extends CardImpl {

    public SurgeOfBrilliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Paradox -- Draw a card for each spell you've cast this turn from anywhere other than your hand.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(SpellsCastNotFromHandValue.instance));
        this.getSpellAbility().setAbilityWord(AbilityWord.PARADOX);
        this.getSpellAbility().addWatcher(new SpellsCastNotFromHandWatcher());

        // Foretell {1}{U}
        this.addAbility(new ForetellAbility(this, "{1}{U}"));
    }

    private SurgeOfBrilliance(final SurgeOfBrilliance card) {
        super(card);
    }

    @Override
    public SurgeOfBrilliance copy() {
        return new SurgeOfBrilliance(this);
    }
}
