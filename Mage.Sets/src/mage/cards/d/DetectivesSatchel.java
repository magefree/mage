package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SacrificedArtifactThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DetectivesSatchel extends CardImpl {

    public DetectivesSatchel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}{R}");

        // When Detective's Satchel enters the battlefield, investigate twice.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect(2)));

        // {T}: Create a 1/1 colorless Thopter artifact creature token with flying. Activate only if you've sacrificed an artifact this turn.
        this.addAbility(new ConditionalActivatedAbility(
                new CreateTokenEffect(new ThopterColorlessToken()), new TapSourceCost(),
                SacrificedArtifactThisTurnCondition.instance
        ).addHint(SacrificedArtifactThisTurnCondition.getHint()), new PermanentsSacrificedWatcher());
    }

    private DetectivesSatchel(final DetectivesSatchel card) {
        super(card);
    }

    @Override
    public DetectivesSatchel copy() {
        return new DetectivesSatchel(this);
    }
}
