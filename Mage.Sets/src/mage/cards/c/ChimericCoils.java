
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class ChimericCoils extends CardImpl {

    public ChimericCoils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {X}{1}: Chimeric Coils becomes an X/X Construct artifact creature. Sacrifice it at the beginning of thhe next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChimericCoilsEffect(), new ManaCostsImpl("{X}{1}"));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect())));
        this.addAbility(ability);
    }

    public ChimericCoils(final ChimericCoils card) {
        super(card);
    }

    @Override
    public ChimericCoils copy() {
        return new ChimericCoils(this);
    }
}

class ChimericCoilsEffect extends ContinuousEffectImpl {

    public ChimericCoilsEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        setText();
    }

    public ChimericCoilsEffect(final ChimericCoilsEffect effect) {
        super(effect);
    }

    @Override
    public ChimericCoilsEffect copy() {
        return new ChimericCoilsEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.addCardType(CardType.CREATURE);
                        permanent.getSubtype(game).add(SubType.CONSTRUCT);
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        int xValue = source.getManaCostsToPay().getX();
                        permanent.getPower().setValue(xValue);
                        permanent.getToughness().setValue(xValue);
                    }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private void setText() {
        staticText = duration.toString() + " {this} becomes an X/X Construct artifact creature";
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }
}
