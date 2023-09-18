package mage.cards.e;

import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class ExoticDisease extends CardImpl {

    public ExoticDisease(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Domain - Target player loses X life and you gain X life, where X is the number of basic land types among lands you control.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(DomainValue.REGULAR)
                .setText("target player loses X life"));
        this.getSpellAbility().addEffect(new GainLifeEffect(DomainValue.REGULAR)
                .setText("and you gain X life, where X is the number of basic land types among lands you control"));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addHint(DomainHint.instance);
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
    }

    private ExoticDisease(final ExoticDisease card) {
        super(card);
    }

    @Override
    public ExoticDisease copy() {
        return new ExoticDisease(this);
    }
}
