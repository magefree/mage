package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhastlyDeathTyrant extends CardImpl {

    private static final FilterPermanent filter
            = new FilterEnchantmentPermanent("enchantment an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public GhastlyDeathTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.BEHOLDER);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // When Ghastly Death Tyrant enters the battlefield, choose one —
        // • Disintegration Ray — Destroy target enchantment an opponent controls. You lose life equal to its mana value.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GhastlyDeathTyrantEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.withFirstModeFlavorWord("Disintegration Ray");

        // • Death Ray — Creatures you control gain deathtouch until end of turn.
        ability.addMode(new Mode(new GainAbilityAllEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        )).withFlavorWord("Death Ray"));
        this.addAbility(ability);
    }

    private GhastlyDeathTyrant(final GhastlyDeathTyrant card) {
        super(card);
    }

    @Override
    public GhastlyDeathTyrant copy() {
        return new GhastlyDeathTyrant(this);
    }
}

class GhastlyDeathTyrantEffect extends OneShotEffect {

    GhastlyDeathTyrantEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target enchantment an opponent controls. You lose life equal to its mana value";
    }

    private GhastlyDeathTyrantEffect(final GhastlyDeathTyrantEffect effect) {
        super(effect);
    }

    @Override
    public GhastlyDeathTyrantEffect copy() {
        return new GhastlyDeathTyrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        permanent.destroy(source, game);
        player.loseLife(permanent.getManaValue(), game, source, false);
        return true;
    }
}
