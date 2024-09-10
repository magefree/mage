package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.PackTacticsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TargNarDemonFangGnoll extends CardImpl {

    public TargNarDemonFangGnoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GNOLL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Pack tactics — Whenever Targ Nar, Demon-Fang Gnoll attacks, if you attacked with creatures with total power 6 or greater this combat, attacking creatures get +1/+0 until end of turn.
        this.addAbility(new PackTacticsAbility(new BoostAllEffect(
                1, 0, Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES, false
        )));

        // {2}{R}{G}: Double Targ Nar's power and toughness until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new TargNarDemonFangGnollEffect(), new ManaCostsImpl<>("{2}{R}{G}")
        ));
    }

    private TargNarDemonFangGnoll(final TargNarDemonFangGnoll card) {
        super(card);
    }

    @Override
    public TargNarDemonFangGnoll copy() {
        return new TargNarDemonFangGnoll(this);
    }
}

class TargNarDemonFangGnollEffect extends OneShotEffect {

    TargNarDemonFangGnollEffect() {
        super(Outcome.Benefit);
        staticText = "double {this}'s power and toughness until end of turn";
    }

    private TargNarDemonFangGnollEffect(final TargNarDemonFangGnollEffect effect) {
        super(effect);
    }

    @Override
    public TargNarDemonFangGnollEffect copy() {
        return new TargNarDemonFangGnollEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BoostSourceEffect(
                permanent.getPower().getValue(),
                permanent.getToughness().getValue(),
                Duration.EndOfTurn
        ), source);
        return true;
    }
}
