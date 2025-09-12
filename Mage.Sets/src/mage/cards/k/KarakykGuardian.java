package mage.cards.k;

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
import mage.watchers.common.DealtDamageThisGameWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarakykGuardian extends CardImpl {

    public KarakykGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // This creature has hexproof if it hasn't dealt damage yet.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                SourceHasntDealtDamageThisGameCondition.instance,
                "{this} has hexproof if it hasn't dealt damage yet"
        )).addHint(SourceHasntDealtDamageThisGameCondition.getHint()), new DealtDamageThisGameWatcher());
    }

    private KarakykGuardian(final KarakykGuardian card) {
        super(card);
    }

    @Override
    public KarakykGuardian copy() {
        return new KarakykGuardian(this);
    }
}
