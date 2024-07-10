package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MahaItsFeathersNight extends CardImpl {

    public MahaItsFeathersNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward--Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost()));

        // Creatures your opponents control have base toughness 1.
        this.addAbility(new SimpleStaticAbility(new MahaItsFeathersNightEffect()));
    }

    private MahaItsFeathersNight(final MahaItsFeathersNight card) {
        super(card);
    }

    @Override
    public MahaItsFeathersNight copy() {
        return new MahaItsFeathersNight(this);
    }
}

class MahaItsFeathersNightEffect extends ContinuousEffectImpl {

    MahaItsFeathersNightEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.Benefit);
        staticText = "creatures your opponents control have base toughness 1";
    }

    private MahaItsFeathersNightEffect(final MahaItsFeathersNightEffect effect) {
        super(effect);
    }

    @Override
    public MahaItsFeathersNightEffect copy() {
        return new MahaItsFeathersNightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            permanent.getToughness().setModifiedBaseValue(1);
        }
        return true;
    }
}
