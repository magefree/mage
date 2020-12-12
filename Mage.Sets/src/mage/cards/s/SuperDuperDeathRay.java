package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.game.combat.CombatGroup.getLethalDamage;

/**
 * @author TheElk801
 */
public final class SuperDuperDeathRay extends CardImpl {

    public SuperDuperDeathRay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Trample
        this.addAbility(new SimpleStaticAbility(new InfoEffect(
                "Trample <i>(This spell can deal excess damage to its target's controller.)</i>"
        )));

        // Super-Duper Death Ray deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new SuperDuperDeathRayEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SuperDuperDeathRay(final SuperDuperDeathRay card) {
        super(card);
    }

    @Override
    public SuperDuperDeathRay copy() {
        return new SuperDuperDeathRay(this);
    }
}

class SuperDuperDeathRayEffect extends OneShotEffect {

    SuperDuperDeathRayEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 4 damage to target creature.";
    }

    private SuperDuperDeathRayEffect(final SuperDuperDeathRayEffect effect) {
        super(effect);
    }

    @Override
    public SuperDuperDeathRayEffect copy() {
        return new SuperDuperDeathRayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent == null || sourceObject == null) {
            return false;
        }
        int lethal = getLethalDamage(permanent, game);
        if (sourceObject.getAbilities().containsKey(DeathtouchAbility.getInstance().getId())) {
            lethal = Math.min(lethal, 1);
        }
        lethal = Math.min(lethal, 4);
        permanent.damage(lethal, source.getSourceId(), source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null && lethal < 4) {
            player.damage(4 - lethal, source.getSourceId(), source, game);
        }
        return true;
    }
}