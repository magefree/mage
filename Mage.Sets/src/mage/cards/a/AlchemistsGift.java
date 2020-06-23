package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlchemistsGift extends CardImpl {

    public AlchemistsGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets +1/+1 and gains your choice of deathtouch or lifelink until end of turn.
        this.getSpellAbility().addEffect(new AlchemistsGiftEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AlchemistsGift(final AlchemistsGift card) {
        super(card);
    }

    @Override
    public AlchemistsGift copy() {
        return new AlchemistsGift(this);
    }
}

class AlchemistsGiftEffect extends OneShotEffect {

    AlchemistsGiftEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature gets +1/+1 and gains your choice of deathtouch or lifelink until end of turn.";
    }

    private AlchemistsGiftEffect(final AlchemistsGiftEffect effect) {
        super(effect);
    }

    @Override
    public AlchemistsGiftEffect copy() {
        return new AlchemistsGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Ability ability = player.chooseUse(
                outcome, "Deathtouch or lifelink?", null,
                "Deathtouch", "Lifelink", source, game
        ) ? DeathtouchAbility.getInstance() : LifelinkAbility.getInstance();
        game.addEffect(new BoostSourceEffect(1, 1, Duration.EndOfTurn), ability);
        game.addEffect(new GainAbilitySourceEffect(ability, Duration.EndOfTurn), ability);
        return true;
    }
}
