package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarvestriteHost extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.RABBIT, "Rabbit");

    public HarvestriteHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Harvestrite Host or another Rabbit you control enters, target creature you control gets +1/+0 until end of turn. Then draw a card if this is the second time this ability has resolved this turn.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BoostTargetEffect(1, 0), filter, false, true
        );
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                2, new DrawCardSourceControllerEffect(1)
        ).setText("Then draw a card if this is the second time this ability has resolved this turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private HarvestriteHost(final HarvestriteHost card) {
        super(card);
    }

    @Override
    public HarvestriteHost copy() {
        return new HarvestriteHost(this);
    }
}
