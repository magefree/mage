package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScaleUp extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("each creature you control");

    public ScaleUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Until end of turn, target creature you control becomes a green Wurm with base power and toughness 6/4.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(6, 4, "green Wurm with base power and toughness 6/4")
                        .withColor("G").withSubType(SubType.WURM),
                true, false, Duration.EndOfTurn, false, true
        ).withDurationRuleAtStart(true));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Overload {4}{G}{G}
        this.addAbility(new OverloadAbility(this, new BecomesCreatureAllEffect(
                new CreatureToken(6, 4, "green Wurm with base power and toughness 6/4")
                        .withColor("G").withSubType(SubType.WURM),
                null, filter, Duration.EndOfTurn, true, false, true
        ), new ManaCostsImpl<>("{4}{G}{G}")));
    }

    private ScaleUp(final ScaleUp card) {
        super(card);
    }

    @Override
    public ScaleUp copy() {
        return new ScaleUp(this);
    }
}
