
package mage.cards.a;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ModeChoice;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Eirkei
 */
public final class AshlingsPrerogative extends CardImpl {

    private static final FilterPermanent filterMatch
            = new FilterCreaturePermanent("each creature with mana value of the chosen quality");
    private static final FilterPermanent filterNotMatch
            = new FilterCreaturePermanent("each creature without mana value of the chosen quality");

    static {
        filterMatch.add(AshlingsPrerogativePredicate.WITH);
        filterNotMatch.add(AshlingsPrerogativePredicate.WITHOUT);
    }

    public AshlingsPrerogative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // As Ashling's Prerogative enters the battlefield, choose odd or even.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.ODD, ModeChoice.EVEN)));

        // Each creature with converted mana cost of the chosen value has haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filterMatch
        )));

        // Each creature without converted mana cost of the chosen value enters the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filterNotMatch)));
    }

    private AshlingsPrerogative(final AshlingsPrerogative card) {
        super(card);
    }

    @Override
    public AshlingsPrerogative copy() {
        return new AshlingsPrerogative(this);
    }
}

enum AshlingsPrerogativePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    WITH(true),
    WITHOUT(false);
    private final boolean match;

    AshlingsPrerogativePredicate(boolean match) {
        this.match = match;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        if (ModeChoice.ODD.checkMode(game, input.getSource())) {
            return (input.getObject().getManaValue() % 2 == 1) == match;
        } else if (ModeChoice.EVEN.checkMode(game, input.getSource())) {
            return (input.getObject().getManaValue() % 2 == 0) == match;
        } else {
            return false;
        }
    }
}
