package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StartlingDevelopment extends CardImpl {

    public StartlingDevelopment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Until end of turn, target creature becomes a blue Serpent with base power and toughness 4/4.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(
                        4, 4, "blue Serpent with base power and toughness 4/4"
                ).withColor("U").withSubType(SubType.SERPENT),
                false, false, Duration.EndOfTurn
        ).withDurationRuleAtStart(true).setRemoveSubtypes(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Cycling {1}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private StartlingDevelopment(final StartlingDevelopment card) {
        super(card);
    }

    @Override
    public StartlingDevelopment copy() {
        return new StartlingDevelopment(this);
    }
}
