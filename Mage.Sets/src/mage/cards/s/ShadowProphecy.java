package mage.cards.s;

import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowProphecy extends CardImpl {

    public ShadowProphecy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Domain - Look at the top X cards of your library, where X is the number of basic land types among lands you control. Put up to two of them into your hand and the rest into your graveyard. You lose 2 life.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                DomainValue.REGULAR, 2,
                LookLibraryControllerEffect.PutCards.HAND,
                LookLibraryControllerEffect.PutCards.GRAVEYARD
        ));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
        this.getSpellAbility().addHint(DomainHint.instance);
    }

    private ShadowProphecy(final ShadowProphecy card) {
        super(card);
    }

    @Override
    public ShadowProphecy copy() {
        return new ShadowProphecy(this);
    }
}
