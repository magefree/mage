package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public final class DoctorDoomUnrivaled extends CardImpl {
    public DoctorDoomUnrivaled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SORCERER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {T}: You draw a card and lose 1 life. Then if your library has no cards in it, you win the game. (You win even if you have 0 life or didn’t draw a card.)
        Ability tapAbility = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1, true), new TapSourceCost());
        tapAbility.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        tapAbility.addEffect(new DoctorDoomUnrivaledEffect());

        this.addAbility(tapAbility);
    }

    private DoctorDoomUnrivaled(final DoctorDoomUnrivaled card) {
        super(card);
    }

    @Override
    public DoctorDoomUnrivaled copy() {
        return new DoctorDoomUnrivaled(this);
    }
}

class DoctorDoomUnrivaledEffect extends OneShotEffect {
    DoctorDoomUnrivaledEffect() {
        super(Outcome.Benefit);
        staticText = "Then if your library has no cards in it, you win the game. <i>(You win even if you have 0 life or didn't draw a card.)</i>";
    }

    private DoctorDoomUnrivaledEffect(final DoctorDoomUnrivaledEffect effect) {
        super(effect);
    }

    @Override
    public DoctorDoomUnrivaledEffect copy() {
        return new DoctorDoomUnrivaledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.getLibrary().size() == 0) {
            player.won(game);
            return true;
        }

        return false;
    }
}

