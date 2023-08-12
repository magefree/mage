package mage.cards.d;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class DeathPitOffering extends CardImpl {

    public DeathPitOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // When Death Pit Offering enters the battlefield, sacrifice all creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DeathPitOfferingEffect()));
        // Creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false)));
    }

    private DeathPitOffering(final DeathPitOffering card) {
        super(card);
    }

    @Override
    public DeathPitOffering copy() {
        return new DeathPitOffering(this);
    }
}

class DeathPitOfferingEffect extends OneShotEffect {

    public DeathPitOfferingEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "sacrifice all creatures you control";
    }

    public DeathPitOfferingEffect(final DeathPitOfferingEffect effect) {
        super(effect);
    }

    @Override
    public DeathPitOfferingEffect copy() {
        return new DeathPitOfferingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source, game);
        }
        return true;
    }
}
