package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SkySwallower extends CardImpl {

    public SkySwallower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sky Swallower enters the battlefield, target opponent gains control of all other permanents you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SkySwallowerEffect());
        ability.addTarget(new TargetOpponent());
        addAbility(ability);
    }

    private SkySwallower(final SkySwallower card) {
        super(card);
    }

    @Override
    public SkySwallower copy() {
        return new SkySwallower(this);
    }
}

class SkySwallowerEffect extends OneShotEffect {

    SkySwallowerEffect() {
        super(Outcome.Detriment);
        this.staticText = "target opponent gains control of all other permanents you control";
    }

    private SkySwallowerEffect(final SkySwallowerEffect effect) {
        super(effect);
    }

    @Override
    public SkySwallowerEffect copy() {
        return new SkySwallowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        return new GainControlAllEffect(Duration.Custom,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_PERMANENT,
                opponent.getId()
        ).apply(game, source);
    }

}
