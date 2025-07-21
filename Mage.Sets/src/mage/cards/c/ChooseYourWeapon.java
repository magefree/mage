package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChooseYourWeapon extends CardImpl {

    public ChooseYourWeapon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose one —
        // • Two-Weapon Fighting — Double target creature's power and toughness until end of turn.
        this.getSpellAbility().addEffect(new ChooseYourWeaponEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Two-Weapon Fighting");

        // • Archery — This spell deals 5 damage to target creature with flying.
        Mode mode = new Mode(new DamageTargetEffect(5, "this spell"));
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.getSpellAbility().addMode(mode.withFlavorWord("Archery"));
    }

    private ChooseYourWeapon(final ChooseYourWeapon card) {
        super(card);
    }

    @Override
    public ChooseYourWeapon copy() {
        return new ChooseYourWeapon(this);
    }
}

class ChooseYourWeaponEffect extends OneShotEffect {

    ChooseYourWeaponEffect() {
        super(Outcome.Benefit);
        staticText = "double target creature's power and toughness until end of turn";
    }

    private ChooseYourWeaponEffect(final ChooseYourWeaponEffect effect) {
        super(effect);
    }

    @Override
    public ChooseYourWeaponEffect copy() {
        return new ChooseYourWeaponEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(
                permanent.getPower().getValue(),
                permanent.getToughness().getValue(),
                Duration.EndOfTurn
        ), source);
        return true;
    }
}
