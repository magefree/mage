
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;

/**
 *
 * @author KholdFuzion & L_J
 *
 */
public final class GiantShark extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("an Island");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature that has been dealt damage this turn");

    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
        filter2.add(new WasDealtDamageThisTurnPredicate());
    }

    public GiantShark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.FISH);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Giant Shark can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(filter)));

        // Whenever Giant Shark blocks or becomes blocked by a creature that has been dealt damage this turn, Giant Shark gets +2/+0 and gains trample until end of turn.
        Ability ability = new BlocksOrBecomesBlockedTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn).setText("{this} gets +2/+0"), filter2, false);
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn).setText("and gains trample until end of turn"));
        this.addAbility(ability);

        // When you control no Islands, sacrifice Giant Shark.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterLandPermanent(SubType.ISLAND, "no Islands"), ComparisonType.EQUAL_TO, 0,
                new SacrificeSourceEffect()));
    }

    public GiantShark(final GiantShark card) {
        super(card);
    }

    @Override
    public GiantShark copy() {
        return new GiantShark(this);
    }
}
