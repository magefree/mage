package mage.cards.v;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Loki
 */
public final class VoicesFromTheVoid extends CardImpl {

    private static final DynamicValue xValue = new DomainValue();

    public VoicesFromTheVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Domain - Target player discards a card for each basic land type among lands you control.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(xValue));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addHint(DomainHint.instance);
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
    }

    private VoicesFromTheVoid(final VoicesFromTheVoid card) {
        super(card);
    }

    @Override
    public VoicesFromTheVoid copy() {
        return new VoicesFromTheVoid(this);
    }
}
