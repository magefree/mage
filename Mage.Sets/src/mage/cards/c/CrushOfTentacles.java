
package mage.cards.c;

import java.util.UUID;
import mage.abilities.condition.common.SurgedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.permanent.token.CrushOfTentaclesToken;

/**
 *
 * @author LevelX2
 */
public final class CrushOfTentacles extends CardImpl {

    public CrushOfTentacles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return all nonland permanents to their owners' hands. If Crush of Tentacles surge cost was paid, create an 8/8 blue Octopus creature token.
        getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(new FilterNonlandPermanent("nonland permanents")));
        Effect effect = new ConditionalOneShotEffect(new CreateTokenEffect(new CrushOfTentaclesToken()), SurgedCondition.instance);
        effect.setText("If this spell's surge cost was paid, create an 8/8 blue Octopus creature token");
        getSpellAbility().addEffect(effect);

        // Surge {3}{U}{U} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        addAbility(new SurgeAbility(this, "{3}{U}{U}"));
    }

    private CrushOfTentacles(final CrushOfTentacles card) {
        super(card);
    }

    @Override
    public CrushOfTentacles copy() {
        return new CrushOfTentacles(this);
    }
}
