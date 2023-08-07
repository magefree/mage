package mage.cards.n;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author noahg
 */
public final class Nightcreep extends CardImpl {

    public Nightcreep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");
        

        // Until end of turn, all creatures become black and all lands become Swamps.
        this.getSpellAbility().addEffect(new NightcreepCreatureEffect());
        this.getSpellAbility().addEffect(new NightcreepLandEffect());
    }

    private Nightcreep(final Nightcreep card) {
        super(card);
    }

    @Override
    public Nightcreep copy() {
        return new Nightcreep(this);
    }
}

class NightcreepLandEffect extends BecomesBasicLandTargetEffect {

    public NightcreepLandEffect() {
        super(Duration.EndOfTurn, SubType.SWAMP);
        this.staticText = "and all lands become Swamps";
    }

    public NightcreepLandEffect(NightcreepLandEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        List<Permanent> targets = new ArrayList<>(game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), source, game));
        this.setTargetPointer(new FixedTargets(targets, game));
    }

    @Override
    public NightcreepLandEffect copy() {
        return new NightcreepLandEffect(this);
    }
}

class NightcreepCreatureEffect extends BecomesColorTargetEffect {

    public NightcreepCreatureEffect() {
        super(ObjectColor.BLACK, Duration.EndOfTurn);
        this.staticText = "Until end of turn, all creatures become black";
    }

    public NightcreepCreatureEffect(NightcreepCreatureEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        List<Permanent> targets = new ArrayList<>(game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game));
        this.setTargetPointer(new FixedTargets(targets, game));
    }

    @Override
    public NightcreepCreatureEffect copy() {
        return new NightcreepCreatureEffect(this);
    }
}
