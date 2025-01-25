package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilentHallcreeper extends CardImpl {

    public SilentHallcreeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Silent Hallcreeper can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever Silent Hallcreeper deals combat damage to a player, choose one that hasn't been chosen --
        // * Put two +1/+1 counters on Silent Hallcreeper.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2))
        );
        ability.setModeTag("add counters");
        ability.getModes().setLimitUsageByOnce(false);

        // * Draw a card.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)).setModeTag("draw a card"));

        // * Silent Hallcreeper becomes a copy of another target creature you control.
        ability.addMode(new Mode(new SilentHallcreeperEffect())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL))
                .setModeTag("copy another creature"));
        this.addAbility(ability);
    }

    private SilentHallcreeper(final SilentHallcreeper card) {
        super(card);
    }

    @Override
    public SilentHallcreeper copy() {
        return new SilentHallcreeper(this);
    }
}

class SilentHallcreeperEffect extends OneShotEffect {

    SilentHallcreeperEffect() {
        super(Outcome.Benefit);
        staticText = "{this} becomes a copy of another target creature you control";
    }

    private SilentHallcreeperEffect(final SilentHallcreeperEffect effect) {
        super(effect);
    }

    @Override
    public SilentHallcreeperEffect copy() {
        return new SilentHallcreeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            game.copyPermanent(Duration.Custom, copyFromPermanent, sourcePermanent.getId(), source, new EmptyCopyApplier());
            return true;
        }
        return false;
    }
}
