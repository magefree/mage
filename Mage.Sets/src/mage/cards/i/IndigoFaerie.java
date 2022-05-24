
package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class IndigoFaerie extends CardImpl {

    public IndigoFaerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {U}: Target permanent becomes blue in addition to its other colors until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesBlueTargetEffect(), new ManaCostsImpl<>("{U}"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
        
    }

    private IndigoFaerie(final IndigoFaerie card) {
        super(card);
    }

    @Override
    public IndigoFaerie copy() {
        return new IndigoFaerie(this);
    }
}

class BecomesBlueTargetEffect extends ContinuousEffectImpl {

    public BecomesBlueTargetEffect() {
        super(Duration.EndOfTurn, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Neutral);
        staticText = "Target permanent becomes blue in addition to its other colors until end of turn";
    }

    public BecomesBlueTargetEffect(final BecomesBlueTargetEffect effect) {
        super(effect);
    }

    @Override
    public BecomesBlueTargetEffect copy() {
        return new BecomesBlueTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.getColor(game).setBlue(true);
            return true;
        }
        return false;
    }

}
