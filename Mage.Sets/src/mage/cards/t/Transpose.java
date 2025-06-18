package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.BlackWizardToken;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Transpose extends CardImpl {

    public Transpose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Draw a card, then discard a card. You lose 1 life. If this spell was cast from your hand, create a 0/1 black Wizard creature token with "Whenever you cast a noncreature spell, this token deals 1 damage to each opponent."
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(1, 1));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new BlackWizardToken()), TransposeCondition.instance
        ));

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private Transpose(final Transpose card) {
        super(card);
    }

    @Override
    public Transpose copy() {
        return new Transpose(this);
    }
}

enum TransposeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Ability::getSourceId)
                .map(game::getSpell)
                .map(Spell::getFromZone)
                .map(Zone.HAND::match)
                .orElse(false);
    }

    @Override
    public String toString() {
        return "this spell was cast from your hand";
    }
}
