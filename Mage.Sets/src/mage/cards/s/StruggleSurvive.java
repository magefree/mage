package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class StruggleSurvive extends SplitCard {

    public StruggleSurvive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{2}{R}", "{1}{G}", SpellAbilityType.SPLIT_AFTERMATH);

        // Struggle
        // Struggle deals damage to target creature equal to the number of lands you control.
        Effect effect = new DamageTargetEffect(new PermanentsOnBattlefieldCount(new FilterControlledLandPermanent("the number of lands you control")));
        effect.setText("Struggle deals damage to target creature equal to the number of lands you control");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Survive
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Each player shuffles their graveyard into their library.
        getRightHalfCard().getSpellAbility().addEffect(new SurviveEffect());

    }

    private StruggleSurvive(final StruggleSurvive card) {
        super(card);
    }

    @Override
    public StruggleSurvive copy() {
        return new StruggleSurvive(this);
    }
}

class SurviveEffect extends OneShotEffect {

    public SurviveEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles their graveyard into their library";
    }

    private SurviveEffect(final SurviveEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (sourcePlayer != null) {
            for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.shuffleCardsToLibrary(player.getGraveyard(), game, source);
                }
            }
        }
        return true;
    }

    @Override
    public SurviveEffect copy() {
        return new SurviveEffect(this);
    }

}
