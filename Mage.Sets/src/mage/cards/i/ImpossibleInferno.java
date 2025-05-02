package mage.cards.i;

import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImpossibleInferno extends CardImpl {

    public ImpossibleInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Impossible Inferno deals 6 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Delirium -- If there are four or more card types among cards in your graveyard, exile the top card of your library. You may play it until the end of your next turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn),
                DeliriumCondition.instance, "<br>" + AbilityWord.DELIRIUM.formatWord() + "If there are " +
                "four or more card types among cards in your graveyard, exile the top card of your library. " +
                "You may play it until the end of your next turn"
        ));
        this.getSpellAbility().addHint(CardTypesInGraveyardCount.YOU.getHint());
    }

    private ImpossibleInferno(final ImpossibleInferno card) {
        super(card);
    }

    @Override
    public ImpossibleInferno copy() {
        return new ImpossibleInferno(this);
    }
}
