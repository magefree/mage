package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class RustedRelic extends CardImpl {

    public RustedRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Metalcraft — Rusted Relic is a 5/5 Golem artifact creature as long as you control three or more artifacts.
        this.addAbility(new SimpleStaticAbility(
            new ConditionalContinuousEffect(
                new BecomesCreatureSourceEffect(
                    new CreatureToken(5, 5, "5/5 Golem artifact creature", SubType.GOLEM).withType(CardType.ARTIFACT),
                    CardType.ARTIFACT,
                    Duration.WhileOnBattlefield
                ),
                MetalcraftCondition.instance,
                "{this} is a 5/5 Golem artifact creature as long as you control three or more artifacts"))
            .setAbilityWord(AbilityWord.METALCRAFT)
            .addHint(MetalcraftHint.instance)
        );
    }

    private RustedRelic(final RustedRelic card) {
        super(card);
    }

    @Override
    public RustedRelic copy() {
        return new RustedRelic(this);
    }
}
