package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.CadetToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author muz
 */
public final class StingerquillCharm extends CardImpl {

    public StingerquillCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{R}");
        

        // Choose one --
        // * Stingerquill Charm deals 3 damage to any target.
        this.spellAbility.addEffect(new DamageTargetEffect(3));
        this.spellAbility.addTarget(new TargetAnyTarget());

        // * Target creature gains first strike and deathtouch until end of turn.
        Mode mode = new Mode(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn)
            .setText("target creature gains first strike"));
        mode.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn)
            .setText("and deathtouch until end of turn"));
        mode.addTarget(new TargetCreaturePermanent());
        this.spellAbility.addMode(mode);

        // * Create a 2/2 colorless Wizard Soldier creature token named Cadet. It gains haste until end of turn.
        this.getSpellAbility().addMode(new Mode(new StingerquillCharmEffect()));
    }

    private StingerquillCharm(final StingerquillCharm card) {
        super(card);
    }

    @Override
    public StingerquillCharm copy() {
        return new StingerquillCharm(this);
    }
}

class StingerquillCharmEffect extends OneShotEffect {

    StingerquillCharmEffect() {
        super(Outcome.Benefit);
        staticText = "create a 2/2 colorless Wizard Soldier creature token named Cadet. It gains haste until end of turn";
    }

    private StingerquillCharmEffect(final StingerquillCharmEffect effect) {
        super(effect);
    }

    @Override
    public StingerquillCharmEffect copy() {
        return new StingerquillCharmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new CadetToken();
        token.putOntoBattlefield(1, game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
            .setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
