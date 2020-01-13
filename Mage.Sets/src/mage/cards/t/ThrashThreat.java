package mage.cards.t;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.permanent.token.RedGreenBeastToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrashThreat extends SplitCard {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public ThrashThreat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{R/G}{R/G}", "{2}{R}{G}", SpellAbilityType.SPLIT);

        // Thrash
        // Target creature you control deals damage equal to its power to target creature or planeswalker you don't control.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        // Threat
        // Create a 4/4 red and green Beast creature token with trample.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new RedGreenBeastToken()));
    }

    private ThrashThreat(final ThrashThreat card) {
        super(card);
    }

    @Override
    public ThrashThreat copy() {
        return new ThrashThreat(this);
    }
}
