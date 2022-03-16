
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GildedDrake extends CardImpl {

    public GildedDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Gilded Drake enters the battlefield, exchange control of Gilded Drake and up to one target creature an opponent controls. If you don't make an exchange, sacrifice Gilded Drake.
        // This ability can't be countered except by spells and abilities.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GildedDrakeEffect());
        ability.setCanFizzle(false);
        ability.addTarget(new TargetCreaturePermanent(0, 1, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, false));
        this.addAbility(ability);
    }

    private GildedDrake(final GildedDrake card) {
        super(card);
    }

    @Override
    public GildedDrake copy() {
        return new GildedDrake(this);
    }
}

class GildedDrakeEffect extends OneShotEffect {

    public GildedDrakeEffect() {
        super(Outcome.GainControl);
        this.staticText = "exchange control of {this} and up to one target creature an opponent controls. If you don't or can't make an exchange, sacrifice {this}. This ability still resolves if its target becomes illegal";
    }

    public GildedDrakeEffect(final GildedDrakeEffect effect) {
        super(effect);
    }

    @Override
    public GildedDrakeEffect copy() {
        return new GildedDrakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourceObject = game.getPermanent(source.getSourceId());
            if (sourceObject != null) {
                if (targetPointer.getFirst(game, source) != null) {
                    Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
                    if (targetPermanent != null) {
                        ContinuousEffect effect = new ExchangeControlTargetEffect(Duration.EndOfGame, "", true);
                        effect.setTargetPointer(targetPointer);
                        game.addEffect(effect, source);
                        return true;
                    }
                }
                sourceObject.sacrifice(source, game);
            }
            return true;
        }
        return false;
    }
}
