package mage.cards.k;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Kaleidoscorch extends CardImpl {

    public Kaleidoscorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Converge — Kaleidoscorch deals X damage to any target, where X is the number of colors of mana spent to cast this spell.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ColorsOfManaSpentToCastCount.getInstance())
                .setText("{this} deals X damage to any target, where X is the number of colors of mana spent to cast this spell"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);

        // Flashback {4}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{R}")));
    }

    private Kaleidoscorch(final Kaleidoscorch card) {
        super(card);
    }

    @Override
    public Kaleidoscorch copy() {
        return new Kaleidoscorch(this);
    }
}
