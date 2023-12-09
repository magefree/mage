
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.CentaurToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author dragonfyre23
 */
public final class GallifreyFallsNoMore extends SplitCard {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("target creatures you control");

    public GallifreyFallsNoMore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}{R}", "{2}{W}", SpellAbilityType.SPLIT_FUSED);

        // Gallifrey Falls
        // Gallifrey Falls deals 4 damage to each creature.
        getLeftHalfCard().getSpellAbility().addEffect(new DamageAllEffect(4, new FilterCreaturePermanent()));

        //If a creature dealt damage this way would die this turn, exile it instead.
        getLeftHalfCard().getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(getLeftHalfCard(), Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addWatcher(new DamagedByWatcher(false));

        // No More
        // Any number of target creatures you control phase out.
        getRightHalfCard().getSpellAbility().addEffect(new PhaseOutTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, false));

    }

    private GallifreyFallsNoMore(final GallifreyFallsNoMore card) {
        super(card);
    }

    @Override
    public GallifreyFallsNoMore copy() {
        return new GallifreyFallsNoMore(this);
    }
}
