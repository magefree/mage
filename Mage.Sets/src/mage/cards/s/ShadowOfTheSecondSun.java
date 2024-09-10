package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ShadowOfTheSecondSun extends CardImpl {

    public ShadowOfTheSecondSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of enchanted player's postcombat main phase, there is an additional beginning phase after this phase.
        this.addAbility(new BeginningOfPostCombatMainTriggeredAbility(
                new ShadowOfTheSecondSunTargetEffect(),
                TargetController.ENCHANTED, false
        ));
    }

    private ShadowOfTheSecondSun(final ShadowOfTheSecondSun card) {
        super(card);
    }

    @Override
    public ShadowOfTheSecondSun copy() {
        return new ShadowOfTheSecondSun(this);
    }
}

class ShadowOfTheSecondSunTargetEffect extends OneShotEffect {

    ShadowOfTheSecondSunTargetEffect() {
        super(Outcome.Benefit);
        this.staticText = "there is an additional beginning phase after this phase";
    }

    private ShadowOfTheSecondSunTargetEffect(final ShadowOfTheSecondSunTargetEffect effect) {
        super(effect);
    }

    @Override
    public ShadowOfTheSecondSunTargetEffect copy() {
        return new ShadowOfTheSecondSunTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = this.getTargetPointer().getFirst(game, source);
        if (playerId == null) {
            return false;
        }
        game.getState().getTurnMods().add(new TurnMod(playerId).withExtraPhase(TurnPhase.BEGINNING));
        return true;
    }
}