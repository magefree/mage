
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class LeylineOfSingularity extends CardImpl {

    public LeylineOfSingularity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // If Leyline of Singularity is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // All nonland permanents are legendary.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetSupertypeAllEffect()));
    }

    private LeylineOfSingularity(final LeylineOfSingularity card) {
        super(card);
    }

    @Override
    public LeylineOfSingularity copy() {
        return new LeylineOfSingularity(this);
    }
}

class SetSupertypeAllEffect extends ContinuousEffectImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();

    public SetSupertypeAllEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
    }

    public SetSupertypeAllEffect(final SetSupertypeAllEffect effect) {
        super(effect);
    }

    @Override
    public SetSupertypeAllEffect copy() {
        return new SetSupertypeAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.addSuperType(SuperType.LEGENDARY);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "All nonland permanents are legendary";
    }
}
