
package mage.cards.r;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ReturnToTheRanks extends CardImpl {
    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ReturnToTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Return X target creature cards with converted mana cost 2 or less from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Return X target creature cards with mana value 2 or less from your graveyard to the battlefield");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private ReturnToTheRanks(final ReturnToTheRanks card) {
        super(card);
    }

    @Override
    public ReturnToTheRanks copy() {
        return new ReturnToTheRanks(this);
    }
}
