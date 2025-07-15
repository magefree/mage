package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoordinatedManeuver extends CardImpl {

    public CoordinatedManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose one --
        // * Coordinated Maneuver deals damage equal to the number of creatures you control to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(CreaturesYouControlCount.PLURAL)
                .setText("{this} deals damage equal to the number of creatures you control to target creature or planeswalker"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);

        // * Destroy target enchantment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetEnchantmentPermanent()));
    }

    private CoordinatedManeuver(final CoordinatedManeuver card) {
        super(card);
    }

    @Override
    public CoordinatedManeuver copy() {
        return new CoordinatedManeuver(this);
    }
}
