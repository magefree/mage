package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExtinguisherBattleship extends CardImpl {

    public ExtinguisherBattleship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{8}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, destroy target noncreature permanent. Then this Spacecraft deals 4 damage to each creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new DamageAllEffect(4, StaticFilters.FILTER_PERMANENT_CREATURE).concatBy("Then"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_CREATURE));
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 5+
        // Flying
        // Trample
        // 10/10
        this.addAbility(new StationLevelAbility(5)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(TrampleAbility.getInstance())
                .withPT(10, 10));
    }

    private ExtinguisherBattleship(final ExtinguisherBattleship card) {
        super(card);
    }

    @Override
    public ExtinguisherBattleship copy() {
        return new ExtinguisherBattleship(this);
    }
}
