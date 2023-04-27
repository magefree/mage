
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Scrapbasket extends CardImpl {

    public Scrapbasket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}: Scrapbasket becomes all colors until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesAllColorsEffect(), new ManaCostsImpl<>("{1}")));
        
    }

    private Scrapbasket(final Scrapbasket card) {
        super(card);
    }

    @Override
    public Scrapbasket copy() {
        return new Scrapbasket(this);
    }
}

class BecomesAllColorsEffect extends ContinuousEffectImpl {

    public BecomesAllColorsEffect() {
        super(Duration.EndOfTurn, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Neutral);
        staticText = "{this} becomes all colors until end of turn";
    }

    public BecomesAllColorsEffect(final BecomesAllColorsEffect effect) {
        super(effect);
    }

    @Override
    public BecomesAllColorsEffect copy() {
        return new BecomesAllColorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.getColor(game).setBlack(true);
            permanent.getColor(game).setBlue(true);
            permanent.getColor(game).setRed(true);
            permanent.getColor(game).setGreen(true);
            permanent.getColor(game).setWhite(true);
            return true;
        }
        return false;
    }

}
