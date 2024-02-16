package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhyrexianAtlas extends CardImpl {

    public PhyrexianAtlas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Corrupted -- Whenever Phyrexian Atlas becomes tapped, each opponent with three or more poison counters loses 1 life.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new PhyrexianAtlasEffect()).setAbilityWord(AbilityWord.CORRUPTED));
    }

    private PhyrexianAtlas(final PhyrexianAtlas card) {
        super(card);
    }

    @Override
    public PhyrexianAtlas copy() {
        return new PhyrexianAtlas(this);
    }
}

class PhyrexianAtlasEffect extends OneShotEffect {

    PhyrexianAtlasEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent who has three or more poison counters loses 1 life";
    }

    private PhyrexianAtlasEffect(final PhyrexianAtlasEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianAtlasEffect copy() {
        return new PhyrexianAtlasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.getCounters().getCount(CounterType.POISON) >= 3) {
                player.loseLife(1, game, source, false);
            }
        }
        return true;
    }
}
