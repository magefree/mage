
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Backfir3
 */
public final class ChimericStaff extends CardImpl {

    public ChimericStaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {X}: Chimeric Staff becomes an X/X Construct artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChimericStaffEffect(), new ManaCostsImpl("{X}")));
    }

    public ChimericStaff(final ChimericStaff card) {
        super(card);
    }

    @Override
    public ChimericStaff copy() {
        return new ChimericStaff(this);
    }
}

class ChimericStaffEffect extends ContinuousEffectImpl {

    public ChimericStaffEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        setText();
    }

    public ChimericStaffEffect(final ChimericStaffEffect effect) {
        super(effect);
    }

    @Override
    public ChimericStaffEffect copy() {
        return new ChimericStaffEffect(this);
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
                        if (xValue != 0) {
                            permanent.getPower().setValue(xValue);
                            permanent.getToughness().setValue(xValue);
                        }
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
