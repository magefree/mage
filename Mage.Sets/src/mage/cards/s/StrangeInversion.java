package mage.cards.s;

import mage.abilities.effects.common.continuous.SwitchPowerToughnessTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class StrangeInversion extends CardImpl {

    public StrangeInversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");
        this.subtype.add(SubType.ARCANE);


        // Switch target creature's power and toughness until end of turn.
        this.getSpellAbility().addEffect(new SwitchPowerToughnessTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("switch power and toughness"));
        // Splice onto Arcane {1}{R}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{1}{R}"));
    }

    private StrangeInversion(final StrangeInversion card) {
        super(card);
    }

    @Override
    public StrangeInversion copy() {
        return new StrangeInversion(this);
    }
}
