
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class SabertoothAlleyCat extends CardImpl {

    public SabertoothAlleyCat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sabertooth Alley Cat attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // {1}{R}: Creatures without defender can't block Sabertooth Alley Cat this turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CantBeBlockedByCreaturesSourceEffect(
                        (FilterCreaturePermanent) new FilterCreaturePermanent().add(Predicates.not(new AbilityPredicate(DefenderAbility.class))),
                        Duration.EndOfTurn
                )
                        .setText("Creatures without defender can't block {this} this turn"),
                new ManaCostsImpl<>("{1}{R}")));
    }

    private SabertoothAlleyCat(final SabertoothAlleyCat card) {
        super(card);
    }

    @Override
    public SabertoothAlleyCat copy() {
        return new SabertoothAlleyCat(this);
    }
}
