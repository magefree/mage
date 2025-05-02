package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarAthlete extends CardImpl {

    public StarAthlete(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Star Athlete attacks, choose up to one target nonland permanent. Its controller may sacrifice it. If they don't, Star Athlete deals 5 damage to that player.
        Ability ability = new AttacksTriggeredAbility(new StarAthleteEffect());
        ability.addTarget(new TargetNonlandPermanent(0, 1));
        this.addAbility(ability);

        // Blitz {3}{R}
        this.addAbility(new BlitzAbility(this, "{3}{R}"));
    }

    private StarAthlete(final StarAthlete card) {
        super(card);
    }

    @Override
    public StarAthlete copy() {
        return new StarAthlete(this);
    }
}

class StarAthleteEffect extends OneShotEffect {

    StarAthleteEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to one target nonland permanent. Its controller may sacrifice it. " +
                "If they don't, {this} deals 5 damage to that player";
    }

    private StarAthleteEffect(final StarAthleteEffect effect) {
        super(effect);
    }

    @Override
    public StarAthleteEffect copy() {
        return new StarAthleteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        return player != null
                && (permanent.canBeSacrificed()
                && player.chooseUse(outcome, "Sacrifice " + permanent.getName() + " or take 5 damage?",
                null, "Sacrifce", "Take 5 damage", source, game)
                && permanent.sacrifice(source, game)
                || player.damage(5, source, game) > 0);
    }
}
