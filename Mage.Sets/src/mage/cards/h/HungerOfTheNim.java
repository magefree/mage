package mage.cards.h;

import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HungerOfTheNim extends CardImpl {

    public HungerOfTheNim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target creature gets +1/+0 until end of turn for each artifact you control.
        getSpellAbility().addEffect(new BoostTargetEffect(ArtifactYouControlCount.instance, StaticValue.get(0), Duration.EndOfTurn));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
        getSpellAbility().addHint(ArtifactYouControlHint.instance);
    }

    private HungerOfTheNim(final HungerOfTheNim card) {
        super(card);
    }

    @Override
    public HungerOfTheNim copy() {
        return new HungerOfTheNim(this);
    }
}
