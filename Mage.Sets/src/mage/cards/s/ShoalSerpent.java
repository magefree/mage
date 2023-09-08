
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ShoalSerpent extends CardImpl {
    
    public ShoalSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        
        // Landfall - Whenever a land enters the battlefield under your control, Shoal Serpent loses defender until end of turn.
        Ability ability = new LandfallAbility(Zone.BATTLEFIELD, new ShoalSerpentEffect(), false);
        this.addAbility(ability);
    }

    private ShoalSerpent(final ShoalSerpent card) {
        super(card);
    }

    @Override
    public ShoalSerpent copy() {
        return new ShoalSerpent(this);
    }
}

class ShoalSerpentEffect extends ContinuousEffectImpl {

    public ShoalSerpentEffect() {
        super(Duration.EndOfTurn, Outcome.AddAbility);
        staticText = "{this} loses defender until end of turn";
    }

    private ShoalSerpentEffect(final ShoalSerpentEffect effect) {
        super(effect);
    }

    @Override
    public ShoalSerpentEffect copy() {
        return new ShoalSerpentEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        permanent.removeAbility(DefenderAbility.getInstance(), source.getSourceId(), game);
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6;
    }

}
