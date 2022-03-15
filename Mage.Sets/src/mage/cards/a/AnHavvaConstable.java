
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class AnHavvaConstable extends CardImpl {

    public AnHavvaConstable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // An-Havva Constable's toughness is equal to 1 plus the number of green creatures on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnHavvaConstableEffect()));
    }

    private AnHavvaConstable(final AnHavvaConstable card) {
        super(card);
    }

    @Override
    public AnHavvaConstable copy() {
        return new AnHavvaConstable(this);
    }
}

class AnHavvaConstableEffect extends ContinuousEffectImpl {

    public AnHavvaConstableEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, Outcome.BoostCreature);
        staticText = "{this}'s toughness is equal to 1 plus the number of green creatures on the battlefield";
    }

    public AnHavvaConstableEffect(final AnHavvaConstableEffect effect) {
        super(effect);
    }

    @Override
    public AnHavvaConstableEffect copy() {
        return new AnHavvaConstableEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject == null) { return false; }

        FilterCreaturePermanent filter = new FilterCreaturePermanent("green creatures");
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        int numberOfGreenCreatures = game.getBattlefield().count(filter, source.getSourceId(), source, game);

        mageObject.getToughness().setValue(1 + numberOfGreenCreatures);

        return true;
    }
}
