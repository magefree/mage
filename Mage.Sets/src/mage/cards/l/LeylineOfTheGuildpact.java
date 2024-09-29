package mage.cards.l;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BecomesAllBasicsControlledEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeylineOfTheGuildpact extends CardImpl {

    public LeylineOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G/W}{G/U}{B/G}{R/G}");

        // If Leyline of the Guildpact is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // Each nonland permanent you control is all colors.
        this.addAbility(new SimpleStaticAbility(new LeylineOfTheGuildpactEffect()));

        // Lands you control are every basic land type in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new BecomesAllBasicsControlledEffect()));
    }

    private LeylineOfTheGuildpact(final LeylineOfTheGuildpact card) {
        super(card);
    }

    @Override
    public LeylineOfTheGuildpact copy() {
        return new LeylineOfTheGuildpact(this);
    }
}

class LeylineOfTheGuildpactEffect extends ContinuousEffectImpl {

    private static final ObjectColor allColors = new ObjectColor("WUBRG");

    LeylineOfTheGuildpactEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "each nonland permanent you control is all colors";
    }

    private LeylineOfTheGuildpactEffect(final LeylineOfTheGuildpactEffect effect) {
        super(effect);
    }

    @Override
    public LeylineOfTheGuildpactEffect copy() {
        return new LeylineOfTheGuildpactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND,
                source.getControllerId(), source, game
        )) {
            permanent.getColor(game).addColor(allColors);
        }
        return true;
    }
}
