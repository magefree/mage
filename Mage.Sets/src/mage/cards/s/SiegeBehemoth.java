
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SiegeBehemoth extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature you control");

    public SiegeBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // As long as Siege Behemoth is attacking, for each creature you control, you may have that creature assign its combat damage as though it weren't blocked.
        // TODO: DamageAsThoughNotBlockedAbility should be done by rule modifying effect instead of adding ability (if controlled creature looses all abilities it should'nt loose this effect)
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilityControlledEffect(DamageAsThoughNotBlockedAbility.getInstance(), Duration.WhileOnBattlefield, filter),
                        SourceAttackingCondition.instance,
                        "As long as {this} is attacking, for each creature you control, you may have that creature assign its combat damage as though it weren't blocked"
                )));
    }

    public SiegeBehemoth(final SiegeBehemoth card) {
        super(card);
    }

    @Override
    public SiegeBehemoth copy() {
        return new SiegeBehemoth(this);
    }
}
