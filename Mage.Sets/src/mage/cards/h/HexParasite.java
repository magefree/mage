package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RemoveUpToAmountCountersEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class HexParasite extends CardImpl {

    public HexParasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{B/P}: Remove up to X counters from target permanent. For each counter removed this way, Hex Parasite gets +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(new HexParasiteEffect(), new ManaCostsImpl<>("{X}{B/P}"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private HexParasite(final HexParasite card) {
        super(card);
    }

    @Override
    public HexParasite copy() {
        return new HexParasite(this);
    }
}

class HexParasiteEffect extends OneShotEffect {

    HexParasiteEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Remove up to X counters from target permanent. For each counter removed this way, {this} gets +1/+0 until end of turn";
    }

    private HexParasiteEffect(final HexParasiteEffect effect) {
        super(effect);
    }

    @Override
    public HexParasiteEffect copy() {
        return new HexParasiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int removed = RemoveUpToAmountCountersEffect.doRemoval(
                GetXValue.instance.calculate(game, source, this),
                getTargetPointer().getFirst(game, source),
                game.getPlayer(source.getControllerId()),
                game, source
        );
        if (removed < 1) {
            return false;
        }
        game.addEffect(new BoostSourceEffect(removed, 0, Duration.EndOfTurn), source);
        return true;
    }
}
