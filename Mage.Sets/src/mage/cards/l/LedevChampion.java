package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierLifelinkToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LedevChampion extends CardImpl {

    public LedevChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Ledev Champion attacks, you may tap any number of untapped creatures you control. Ledev Champion gets +1/+1 until end of turn for each creature tapped this way.
        this.addAbility(new AttacksTriggeredAbility(
                new LedevChampionEffect(), false
        ));

        // {3}{G}{W}: Create a 1/1 white soldier creature token with lifelink.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new SoldierLifelinkToken()),
                new ManaCostsImpl<>("{3}{G}{W}")
        ));
    }

    private LedevChampion(final LedevChampion card) {
        super(card);
    }

    @Override
    public LedevChampion copy() {
        return new LedevChampion(this);
    }
}

class LedevChampionEffect extends OneShotEffect {

    public LedevChampionEffect() {
        super(Outcome.GainLife);
        staticText = "you may tap any number of untapped creatures you control. "
                + "{this} gets +1/+1 until end of turn for each creature tapped this way.";
    }

    private LedevChampionEffect(final LedevChampionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES, true);
        target.choose(Outcome.Tap, source.getControllerId(), source.getSourceId(), source, game);
        int tappedAmount = 0;
        for (UUID creatureId : target.getTargets()) {
            Permanent creature = game.getPermanent(creatureId);
            if (creature != null) {
                creature.tap(source, game);
                tappedAmount++;
            }
        }
        if (tappedAmount > 0) {
            game.addEffect(new BoostSourceEffect(tappedAmount, tappedAmount, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }

    @Override
    public LedevChampionEffect copy() {
        return new LedevChampionEffect(this);
    }

}
