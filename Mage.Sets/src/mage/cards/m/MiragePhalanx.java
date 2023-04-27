package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class MiragePhalanx extends CardImpl {

    private static final String ruleText =
            "As long as {this} is paired with another creature, each of those creatures has " +
                    "\"At the beginning of combat on your turn, create a token that's a copy of this creature, " +
                    "except it has haste and loses soulbond. " +
                    "Exile it at end of combat.\"";

    public MiragePhalanx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Mirage Phalanx is paired with another creature, each of those creatures has
        // “At the beginning of combat on your turn, create a token that's a copy of this creature,
        // except it has haste and loses soulbond.
        // Exile it at end of combat.”
        Ability ability = new BeginningOfCombatTriggeredAbility(new MiragePhalanxEffect(), TargetController.YOU, false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(ability, ruleText)));
    }

    private MiragePhalanx(final MiragePhalanx card) {
        super(card);
    }

    @Override
    public MiragePhalanx copy() {
        return new MiragePhalanx(this);
    }
}

class MiragePhalanxEffect extends OneShotEffect {
    MiragePhalanxEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of this creature, " +
                "except it has haste and loses soulbond. " +
                "Exile it at end of combat.";
    }

    private MiragePhalanxEffect(final MiragePhalanxEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) { return false; }

        // It has haste
        CreateTokenCopyTargetEffect tokenCopyEffect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
        tokenCopyEffect.setTargetPointer(new FixedTarget(permanent, game));
        // It loses soulbond
        tokenCopyEffect.addAbilityClassesToRemoveFromTokens(SoulbondAbility.class);
        // Create the token(s)
        tokenCopyEffect.apply(game, source);
        // Exile it at the end of combat
        tokenCopyEffect.exileTokensCreatedAtEndOfCombat(game, source);

        return !tokenCopyEffect.getAddedPermanents().isEmpty();
    }

    @Override
    public Effect copy() { return new MiragePhalanxEffect(this); }
}