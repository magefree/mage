package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAllEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author LevelX2
 */
public final class Dragonshift extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("each creature you control");

    public Dragonshift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");

        // Until end of turn, target creature you control becomes a blue and red Dragon with base power and toughness 4/4, loses all abilities, and gains flying.
        Effect effect = new BecomesCreatureTargetEffect(
                new CreatureToken(4, 4, "blue and red Dragon with base power and toughness 4/4")
                        .withSubType(SubType.DRAGON)
                        .withColor("UR")
                        .withAbility(FlyingAbility.getInstance()),
                true, false, Duration.EndOfTurn)
                .withDurationRuleAtStart(true);
        effect.setText("Until end of turn, target creature you control becomes a blue and red Dragon with base power and toughness 4/4, loses all abilities, and gains flying.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Overload {3}{U}{U}{R}{R}
        Ability ability = new OverloadAbility(this, new LoseAllAbilitiesAllEffect(new FilterControlledCreaturePermanent(""), Duration.EndOfTurn), new ManaCostsImpl<>("{3}{U}{U}{R}{R}"));
        ability.addEffect(new BecomesCreatureAllEffect(
                new CreatureToken(4, 4, "blue and red Dragon with base power and toughness 4/4 and with flying")
                        .withColor("UR")
                        .withSubType(SubType.DRAGON)
                        .withAbility(FlyingAbility.getInstance()),
                null, filter, Duration.EndOfTurn, true, false, true));
        this.addAbility(ability);
    }

    private Dragonshift(final Dragonshift card) {
        super(card);
    }

    @Override
    public Dragonshift copy() {
        return new Dragonshift(this);
    }
}