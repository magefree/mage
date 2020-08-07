package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class BlitzLeech extends CardImpl {

    public BlitzLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Blitz Leech enters the battlefield, target creature an opponent controls gets -2/-2 until end of turn. Remove all counters from that creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-2, -2));
        ability.addEffect(new BlitzLeechEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private BlitzLeech(final BlitzLeech card) {
        super(card);
    }

    @Override
    public BlitzLeech copy() {
        return new BlitzLeech(this);
    }
}

class BlitzLeechEffect extends OneShotEffect {

    BlitzLeechEffect() {
        super(Outcome.Benefit);
        staticText = "Remove all counters from that creature.";
    }

    private BlitzLeechEffect(final BlitzLeechEffect effect) {
        super(effect);
    }

    @Override
    public BlitzLeechEffect copy() {
        return new BlitzLeechEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Set<String> counterTypes = permanent
                .getCounters(game)
                .keySet()
                .stream()
                .collect(Collectors.toSet());
        counterTypes.forEach(permanent.getCounters(game)::removeAllCounters);
        return true;
    }
}