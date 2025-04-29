package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainAnchorWordAbilitySourceEffect;
import mage.abilities.effects.common.replacement.AdditionalTriggersAttackingReplacementEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ModeChoice;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WindcragSiege extends CardImpl {

    public WindcragSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{W}");

        // As this enchantment enters, choose Mardu or Jeskai.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.MARDU, ModeChoice.JESKAI)));

        // * Mardu -- If a creature attacking causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(
                new AdditionalTriggersAttackingReplacementEffect(false), ModeChoice.MARDU
        )));

        // * Jeskai -- At the beginning of your upkeep, create a 1/1 red Goblin creature token. It gains lifelink and haste until end of turn.
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(
                new BeginningOfUpkeepTriggeredAbility(new WindcragSiegeEffect()), ModeChoice.JESKAI
        )));
    }

    private WindcragSiege(final WindcragSiege card) {
        super(card);
    }

    @Override
    public WindcragSiege copy() {
        return new WindcragSiege(this);
    }
}

class WindcragSiegeEffect extends OneShotEffect {

    WindcragSiegeEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 red Goblin creature token. It gains lifelink and haste until end of turn";
    }

    private WindcragSiegeEffect(final WindcragSiegeEffect effect) {
        super(effect);
    }

    @Override
    public WindcragSiegeEffect copy() {
        return new WindcragSiegeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new GoblinToken();
        token.putOntoBattlefield(1, game, source);
        game.addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(token, game)), source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
