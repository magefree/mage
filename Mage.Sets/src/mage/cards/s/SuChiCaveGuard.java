package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuChiCaveGuard extends CardImpl {

    public SuChiCaveGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward {4}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{4}")));

        // When Su-Chi Cave Guard dies, add eight {C}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new DiesSourceTriggeredAbility(new SuChiCaveGuardEffect()));
    }

    private SuChiCaveGuard(final SuChiCaveGuard card) {
        super(card);
    }

    @Override
    public SuChiCaveGuard copy() {
        return new SuChiCaveGuard(this);
    }
}

class SuChiCaveGuardEffect extends OneShotEffect {

    SuChiCaveGuardEffect() {
        super(Outcome.Benefit);
        staticText = "add eight {C}. Until end of turn, you don't lose this mana as steps and phases end";
    }

    private SuChiCaveGuardEffect(final SuChiCaveGuardEffect effect) {
        super(effect);
    }

    @Override
    public SuChiCaveGuardEffect copy() {
        return new SuChiCaveGuardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().addMana(Mana.ColorlessMana(8), game, source, true);
            return true;
        }
        return false;
    }
}
