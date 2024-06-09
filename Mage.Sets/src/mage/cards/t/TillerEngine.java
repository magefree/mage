package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TillerEngine extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public TillerEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a land enters the battlefield tapped and under your control, choose one —
        // • Untap that land.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new TillerEngineUntapEffect(), filter)
                .setTriggerPhrase("Whenever a land enters the battlefield tapped and under your control, ");

        // • Tap target nonland permanent an opponent controls.
        Mode mode = new Mode(new TapTargetEffect());
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private TillerEngine(final TillerEngine card) {
        super(card);
    }

    @Override
    public TillerEngine copy() {
        return new TillerEngine(this);
    }
}

class TillerEngineUntapEffect extends OneShotEffect {

    // This class is needed rather than UntapTargetEffect to use the stored value rather than a SetTargetPointer,
    // because the second mode must not have its target pointer overwritten by the trigger

    TillerEngineUntapEffect() {
        super(Outcome.Untap);
        staticText = "Untap that land";
    }

    private TillerEngineUntapEffect(final TillerEngineUntapEffect effect) {
        super(effect);
    }

    @Override
    public TillerEngineUntapEffect copy() {
        return new TillerEngineUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent != null) {
            permanent.untap(game);
        }
        return true;
    }
}
