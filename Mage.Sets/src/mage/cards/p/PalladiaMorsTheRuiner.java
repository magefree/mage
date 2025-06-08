package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasntDealtDamageThisGameCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.DealtDamageThisGameWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PalladiaMorsTheRuiner extends CardImpl {

    public PalladiaMorsTheRuiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Palladia-Mors, the Ruiner has hexproof if it hasn't dealt damage yet.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                SourceHasntDealtDamageThisGameCondition.instance,
                "{this} has hexproof if it hasn't dealt damage yet"
        )).addHint(SourceHasntDealtDamageThisGameCondition.getHint()), new DealtDamageThisGameWatcher());
    }

    private PalladiaMorsTheRuiner(final PalladiaMorsTheRuiner card) {
        super(card);
    }

    @Override
    public PalladiaMorsTheRuiner copy() {
        return new PalladiaMorsTheRuiner(this);
    }
}
