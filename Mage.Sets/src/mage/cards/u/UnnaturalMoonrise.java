package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.DayNightHint;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnnaturalMoonrise extends CardImpl {

    public UnnaturalMoonrise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{G}");

        // It becomes night. Until end of turn, target creature gets +1/+0 and gains trample and has "Whenever this creature deals combat damage to a player, draw a card."
        this.getSpellAbility().addEffect(new UnnaturalMoonriseEffect());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
                .setText("Until end of turn, target creature gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), false
                ).setTriggerPhrase("Whenever this creature deals combat damage to a player, "), Duration.EndOfTurn
        ).setText("and \"Whenever this creature deals combat damage to a player, draw a card.\""));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(DayNightHint.instance);

        // Flashback {2}{R}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{R}{G}")));
    }

    private UnnaturalMoonrise(final UnnaturalMoonrise card) {
        super(card);
    }

    @Override
    public UnnaturalMoonrise copy() {
        return new UnnaturalMoonrise(this);
    }
}

class UnnaturalMoonriseEffect extends OneShotEffect {

    UnnaturalMoonriseEffect() {
        super(Outcome.Benefit);
        staticText = "It becomes night.";
    }

    private UnnaturalMoonriseEffect(final UnnaturalMoonriseEffect effect) {
        super(effect);
    }

    @Override
    public UnnaturalMoonriseEffect copy() {
        return new UnnaturalMoonriseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.setDaytime(false);
        return true;
    }
}
