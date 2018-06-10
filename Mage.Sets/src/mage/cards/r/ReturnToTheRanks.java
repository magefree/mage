
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class ReturnToTheRanks extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with converted mana cost 2 or less from your graveyard");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ReturnToTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Return X target creature cards with converted mana cost 2 or less from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Return X target creature cards with converted mana cost 2 or less from your graveyard to the battlefield");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,Integer.MAX_VALUE, filter));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        for (Effect effect : ability.getEffects()) {
            if (effect instanceof ReturnFromGraveyardToBattlefieldTargetEffect) {
                int xValue = ability.getManaCostsToPay().getX();
                ability.getTargets().clear();
                ability.addTarget(new TargetCardInYourGraveyard(xValue,xValue, filter));
            }
        }
    }

    public ReturnToTheRanks(final ReturnToTheRanks card) {
        super(card);
    }

    @Override
    public ReturnToTheRanks copy() {
        return new ReturnToTheRanks(this);
    }
}
