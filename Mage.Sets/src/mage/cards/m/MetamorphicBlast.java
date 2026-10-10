package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MetamorphicBlast extends CardImpl {

    public MetamorphicBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Spree
        this.addAbility(new SpreeAbility(this));
        
        // + {1} -- Until end of turn, target creature becomes a white Rabbit with base power and toughness 0/1.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(new CreatureToken(
                0, 1, "white Rabbit with base power and toughness 0/1"
        ).withSubType(SubType.RABBIT).withColor("W"), false, false, Duration.EndOfTurn)
                .withDurationRuleAtStart(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(1));

        // + {3} -- Target player draws two cards.
        this.getSpellAbility().addMode(new Mode(new DrawCardTargetEffect(2))
                .addTarget(new TargetPlayer())
                .withCost(new GenericManaCost(3)));
    }

    private MetamorphicBlast(final MetamorphicBlast card) {
        super(card);
    }

    @Override
    public MetamorphicBlast copy() {
        return new MetamorphicBlast(this);
    }
}
