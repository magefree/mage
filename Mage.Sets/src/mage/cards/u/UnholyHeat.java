package mage.cards.u;

import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnholyHeat extends CardImpl {

    public UnholyHeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Unholy Heat deals 2 damage to target creature or planeswalker.
        // Delirium — Unholy Heat deals 6 damage instead if there are four or more card types among cards in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(6), new DamageTargetEffect(2),
                DeliriumCondition.instance, "{this} deals 2 damage to target creature " +
                "or planeswalker.<br><i>Delirium</i> &mdash; {this} deals 6 damage instead " +
                "if there are four or more card types among cards in your graveyard."
        ));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addHint(CardTypesInGraveyardHint.YOU);
    }

    private UnholyHeat(final UnholyHeat card) {
        super(card);
    }

    @Override
    public UnholyHeat copy() {
        return new UnholyHeat(this);
    }
}
