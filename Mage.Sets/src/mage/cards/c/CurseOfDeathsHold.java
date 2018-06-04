
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class CurseOfDeathsHold extends CardImpl {

    public CurseOfDeathsHold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Creatures enchanted player controls get -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CurseOfDeathsHoldEffect()));
    }

    public CurseOfDeathsHold(final CurseOfDeathsHold card) {
        super(card);
    }

    @Override
    public CurseOfDeathsHold copy() {
        return new CurseOfDeathsHold(this);
    }
}

class CurseOfDeathsHoldEffect extends ContinuousEffectImpl {

    public CurseOfDeathsHoldEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.UnboostCreature);
        staticText = "Creatures enchanted player controls get -1/-1";
    }

    public CurseOfDeathsHoldEffect(final CurseOfDeathsHoldEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player player = game.getPlayer(enchantment.getAttachedTo());
            if (player != null) {
                for (Permanent perm : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
                    perm.addPower(-1);
                    perm.addToughness(-1);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public CurseOfDeathsHoldEffect copy() {
        return new CurseOfDeathsHoldEffect(this);
    }
}
