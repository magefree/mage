package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.DealtDamageThisGameWatcher;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasntDealtDamageThisGameCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RuricTharMagecrusher extends CardImpl {

    public RuricTharMagecrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // This spell can't be countered.
        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ruric Thar has hexproof as long as they haven't dealt combat damage yet.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(HexproofAbility.getInstance()),
            SourceHasntDealtDamageThisGameCondition.instance,
            "{this} has hexproof as long as they haven't dealt damage yet"
        )).addHint(SourceHasntDealtDamageThisGameCondition.getHint()), new DealtDamageThisGameWatcher());
    }

    private RuricTharMagecrusher(final RuricTharMagecrusher card) {
        super(card);
    }

    @Override
    public RuricTharMagecrusher copy() {
        return new RuricTharMagecrusher(this);
    }
}
