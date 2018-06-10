

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;

/**
 *
 * @author LevelX
 */
public final class PainwrackerOni extends CardImpl {

    public PainwrackerOni (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Fear (This creature can't be blocked except by artifact creatures and/or black creatures.)
        this.addAbility(FearAbility.getInstance());
        
        // At the beginning of your upkeep, sacrifice a creature if you don't control an Ogre.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PainwrackerOniEffect(new FilterControlledCreaturePermanent(), 1, ""), TargetController.YOU, false));
    }

    public PainwrackerOni (final PainwrackerOni card) {
        super(card);
    }

    @Override
    public PainwrackerOni copy() {
        return new PainwrackerOni(this);
    }

}

class PainwrackerOniEffect extends SacrificeControllerEffect {
    
    public PainwrackerOniEffect(FilterPermanent filter, int count, String preText) {
        super(filter, count, preText);
        this.staticText = "sacrifice a creature if you don't control an Ogre";
    }
    
    public PainwrackerOniEffect(final PainwrackerOniEffect effect) {
        super(effect);
    }
    
    @Override
    public PainwrackerOniEffect copy() {
        return new PainwrackerOniEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getBattlefield().countAll(new FilterCreaturePermanent(SubType.OGRE, "Ogre"), source.getControllerId(), game) < 1) {
            return super.apply(game, source);
        }
        return true;
    }
}
