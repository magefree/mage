package mage.cards.w;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainAllCreatureTypesTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WingsOfVelisVel extends CardImpl {

    public WingsOfVelisVel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.INSTANT}, "{1}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Target creature becomes 4/4, gains all creature types, and gains flying until end of turn.
        this.getSpellAbility().addEffect(new SetPowerToughnessTargetEffect(4, 4, Duration.EndOfTurn)
                .setText("Until end of turn, target creature has base power and toughness 4/4"));
        this.getSpellAbility().addEffect(new GainAllCreatureTypesTargetEffect(Duration.EndOfTurn)
                .setText(", gains all creature types"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                .setText(", and gains flying"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WingsOfVelisVel(final WingsOfVelisVel card) {
        super(card);
    }

    @Override
    public WingsOfVelisVel copy() {
        return new WingsOfVelisVel(this);
    }
}
