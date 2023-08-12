
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.HalfValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author Quercitron
 */
public final class RakdosTheDefiler extends CardImpl {

    private static final FilterPermanent attackTriggerFilter = new FilterControlledPermanent("the non-Demon permanents you control");
    private static final FilterPermanent damageToPlayerTriggerFilter = new FilterPermanent("the non-Demon permanents you control");

    static {
        attackTriggerFilter.add(Predicates.not(SubType.DEMON.getPredicate()));
        damageToPlayerTriggerFilter.add(Predicates.not(SubType.DEMON.getPredicate()));
    }

    public RakdosTheDefiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Rakdos the Defiler attacks, sacrifice half the non-Demon permanents you control, rounded up.
        Effect effect = new SacrificeControllerEffect(attackTriggerFilter, new HalfValue(new PermanentsOnBattlefieldCount(attackTriggerFilter), true), "");
        effect.setText("sacrifice half the non-Demon permanents you control, rounded up");
        Ability ability = new AttacksTriggeredAbility(effect, false);
        this.addAbility(ability);

        // Whenever Rakdos deals combat damage to a player, that player sacrifices half the non-Demon permanents they control, rounded up.
        effect = new SacrificeEffect(damageToPlayerTriggerFilter, new HalfValue(new PermanentsTargetOpponentControlsCount(damageToPlayerTriggerFilter), true), "");
        effect.setText("that player sacrifices half the non-Demon permanents they control, rounded up");
        ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, false, true);
        this.addAbility(ability);
    }

    private RakdosTheDefiler(final RakdosTheDefiler card) {
        super(card);
    }

    @Override
    public RakdosTheDefiler copy() {
        return new RakdosTheDefiler(this);
    }
}
