package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathRattleOni extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(CreaturesDiedThisTurnCount.instance, 2);
    private static final FilterPermanent filter
            = new FilterCreaturePermanent("other creatures that were dealt damage this turn");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public DeathRattleOni(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // This spell costs 2 less to cast for each creature that died this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(xValue)
                .setText("this spell costs {2} less to cast for each creature that died this turn")
        ).addHint(CreaturesDiedThisTurnHint.instance).setRuleAtTheTop(true));

        // When Death-Rattle Oni enters the battlefield, destroy all other creatures that were dealt damage this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter)));
    }

    private DeathRattleOni(final DeathRattleOni card) {
        super(card);
    }

    @Override
    public DeathRattleOni copy() {
        return new DeathRattleOni(this);
    }
}
