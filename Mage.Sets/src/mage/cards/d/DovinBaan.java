
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.command.emblems.DovinBaanEmblem;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class DovinBaan extends CardImpl {

    public DovinBaan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOVIN);

        this.setStartingLoyalty(3);

        // +1: Until your next turn, up to one target creature gets -3/-0 and its activated abilities can't be activated.
        Effect effect = new BoostTargetEffect(-3, 0, Duration.UntilYourNextTurn);
        effect.setText("Until your next turn, up to one target creature gets -3/-0");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addEffect(new DovinBaanCantActivateAbilitiesEffect());
        this.addAbility(ability);

        // -1: You gain 2 life and draw a card.
        ability = new LoyaltyAbility(new GainLifeEffect(2), -1);
        effect = new DrawCardSourceControllerEffect(1);
        effect.setText("and draw a card");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -7: You get an emblem with "Your opponents can't untap more than two permanents during their untap steps."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new DovinBaanEmblem()), -7));
    }

    private DovinBaan(final DovinBaan card) {
        super(card);
    }

    @Override
    public DovinBaan copy() {
        return new DovinBaan(this);
    }
}

class DovinBaanCantActivateAbilitiesEffect extends ContinuousRuleModifyingEffectImpl {

    DovinBaanCantActivateAbilitiesEffect() {
        super(Duration.UntilYourNextTurn, Outcome.UnboostCreature);
        staticText = "and its activated abilities can't be activated";
    }

    DovinBaanCantActivateAbilitiesEffect(final DovinBaanCantActivateAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public DovinBaanCantActivateAbilitiesEffect copy() {
        return new DovinBaanCantActivateAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(this.getTargetPointer().getFirst(game, source));
    }
}
