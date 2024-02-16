package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class ManaWeb extends CardImpl {

    public ManaWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a land an opponent controls is tapped for mana, tap all lands that player controls that could produce any type of mana that land could produce.
        this.addAbility(new ManaWebTriggeredAbility());
    }

    private ManaWeb(final ManaWeb card) {
        super(card);
    }

    @Override
    public ManaWeb copy() {
        return new ManaWeb(this);
    }
}

class ManaWebTriggeredAbility extends TriggeredAbilityImpl {

    ManaWebTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ManaWebeffect(), false);
    }

    private static final String staticText = "Whenever a land an opponent controls is tapped for mana, " +
            "tap all lands that player controls that could produce any type of mana that land could produce.";

    private ManaWebTriggeredAbility(ManaWebTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // it's non mana triggered ability, so ignore it on checking, see TAPPED_FOR_MANA
        if (game.inCheckPlayableState()) {
            return false;
        }
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        if (permanent == null
                || !permanent.isLand(game)
                || !game.getOpponents(permanent.getControllerId()).contains(getControllerId())) {
            return false;
        }
        this.getEffects().setValue("tappedPermanent", permanent);
        return true;
    }

    @Override
    public ManaWebTriggeredAbility copy() {
        return new ManaWebTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}

class ManaWebeffect extends OneShotEffect {

    ManaWebeffect() {
        super(Outcome.Tap);
    }

    private ManaWebeffect(final ManaWebeffect effect) {
        super(effect);
    }

    @Override
    public ManaWebeffect copy() {
        return new ManaWebeffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("tappedPermanent");
        if (permanent == null) {
            return false;
        }
        Set<ManaType> manaTypesSource = AnyColorLandsProduceManaAbility.getManaTypesFromPermanent(permanent, game);
        boolean tappedLands = false;
        for (Permanent opponentPermanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                permanent.getControllerId(), source, game
        )) {
            Set<ManaType> manaTypes = AnyColorLandsProduceManaAbility.getManaTypesFromPermanent(opponentPermanent, game);
            if (!Collections.disjoint(manaTypes, manaTypesSource)) {
                opponentPermanent.tap(source, game);
            }
        }
        return tappedLands;
    }
}
