package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkulkingKiller extends CardImpl {

    public SkulkingKiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Skulking Killer enters the battlefield, target creature an opponent controls gets -2/-2 until end of turn if that opponent controls no other creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SkulkingKillerEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private SkulkingKiller(final SkulkingKiller card) {
        super(card);
    }

    @Override
    public SkulkingKiller copy() {
        return new SkulkingKiller(this);
    }
}

class SkulkingKillerEffect extends OneShotEffect {

    SkulkingKillerEffect() {
        super(Outcome.Benefit);
        staticText = "target creature an opponent controls gets -2/-2 " +
                "until end of turn if that opponent controls no other creatures";
    }

    private SkulkingKillerEffect(final SkulkingKillerEffect effect) {
        super(effect);
    }

    @Override
    public SkulkingKillerEffect copy() {
        return new SkulkingKillerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                permanent.getControllerId(), source, game
        ) > 1) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(-2, -2), source);
        return true;
    }
}
