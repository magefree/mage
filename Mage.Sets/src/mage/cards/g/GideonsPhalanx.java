
package mage.cards.g;

import java.util.UUID;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.KnightToken;

/**
 *
 * @author fireshoes
 */
public final class GideonsPhalanx extends CardImpl {

    public GideonsPhalanx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{W}{W}");

        // Create four 2/2 white Knight creature tokens with vigilance.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new KnightToken(), 4));

        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, creatures you control gain indestructible until end of turn.
        Effect effect = new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent())),
                SpellMasteryCondition.instance,
                "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, creatures you control gain indestructible until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private GideonsPhalanx(final GideonsPhalanx card) {
        super(card);
    }

    @Override
    public GideonsPhalanx copy() {
        return new GideonsPhalanx(this);
    }
}
