
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WordOfBinding extends CardImpl {

    public WordOfBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Tap X target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap X target creatures"));
        this.getSpellAbility().setTargetAdjuster(WordOfBindingAdjuster.instance);
    }

    private WordOfBinding(final WordOfBinding card) {
        super(card);
    }

    @Override
    public WordOfBinding copy() {
        return new WordOfBinding(this);
    }
}

enum WordOfBindingAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}