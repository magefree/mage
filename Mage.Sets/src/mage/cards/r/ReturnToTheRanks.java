
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ReturnToTheRanks extends CardImpl {

    public ReturnToTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Return X target creature cards with converted mana cost 2 or less from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Return X target creature cards with mana value 2 or less from your graveyard to the battlefield");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(ReturnToTheRanksAdjuster.instance);
    }

    private ReturnToTheRanks(final ReturnToTheRanks card) {
        super(card);
    }

    @Override
    public ReturnToTheRanks copy() {
        return new ReturnToTheRanks(this);
    }
}

enum ReturnToTheRanksAdjuster implements TargetAdjuster {
    instance;
    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(xValue, xValue, filter));
    }
}