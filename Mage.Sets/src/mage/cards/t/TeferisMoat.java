package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;


/**
 * @author Markedagain
 */
public final class TeferisMoat extends CardImpl {

    public TeferisMoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{U}");

        // As Teferi's Moat enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));
        // Creatures of the chosen color without flying can't attack you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TeferisMoatRestrictionEffect()));
    }

    private TeferisMoat(final TeferisMoat card) {
        super(card);
    }

    @Override
    public TeferisMoat copy() {
        return new TeferisMoat(this);
    }
}

class TeferisMoatRestrictionEffect extends RestrictionEffect {

    TeferisMoatRestrictionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures of the chosen color without flying can't attack you";
    }

    TeferisMoatRestrictionEffect(final TeferisMoatRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        ObjectColor chosenColor = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        return chosenColor != null &&
                !permanent.getAbilities().contains(FlyingAbility.getInstance()) &&
                permanent.getColor(game).shares(chosenColor) &&
                permanent.isCreature(game);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        return !defenderId.equals(source.getControllerId());
    }

    @Override
    public TeferisMoatRestrictionEffect copy() {
        return new TeferisMoatRestrictionEffect(this);
    }
}
