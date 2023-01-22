package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DifferentManaValuesInGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.DifferentManaValuesInGraveHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 * @author chesse20
 */

public final class GraveyardShiftAlchemy extends CardImpl {

   public GraveyardShiftAlchemy(UUID ownerId, CardSetInfo setInfo) {
       super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

       // This spell has flash as long as there are five or more mana values among cards in your graveyard.
       this.addAbility(new SimpleStaticAbility(
               Zone.ALL,
               new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                       FlashAbility.getInstance(), Duration.WhileOnBattlefield, true
               ), DifferentManaValuesInGraveCondition.FIVE, "this spell has flash " +
                       "as long as there are five or more mana values among cards in your graveyard")
       ).setRuleAtTheTop(true).addHint(DifferentManaValuesInGraveHint.instance));

       // Return target creature card from your graveyard to the battlefield.
       //this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
       //this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
       
       //code edited from Miraculous Recovery
       this.getSpellAbility().addEffect(new GraveyardShiftAlchemyEffect());
   }

   private GraveyardShiftAlchemy(final GraveyardShiftAlchemy card) {
       super(card);
   }

   @Override
   public GraveyardShiftAlchemy copy() {
       return new GraveyardShiftAlchemy(this);
   }
}
class GraveyardShiftAlchemyEffect extends OneShotEffect {

    public GraveyardShiftAlchemyEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "with an additional +1/+1 counter on it";
    }

    public GraveyardShiftAlchemyEffect(final GraveyardShiftAlchemyEffect effect) {
        super(effect);
    }

    @Override
    public GraveyardShiftAlchemyEffect copy() {
        return new GraveyardShiftAlchemyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // targetPointer can't be used because target moved from graveyard to battlefield
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return false;
    }
}