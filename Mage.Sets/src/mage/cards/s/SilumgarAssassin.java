
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesWithGreaterPowerEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SilumgarAssassin extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 3 or less an opponent controls");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SilumgarAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Creatures with power greater than Silumgar Assassin's power can't block it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByCreaturesWithGreaterPowerEffect()));

        // Megamorph {2}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{B}"), true));

        // When Silumgar Assassin is turned face up, destroy target creature with power 3 or less an opponent controls.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    private SilumgarAssassin(final SilumgarAssassin card) {
        super(card);
    }

    @Override
    public SilumgarAssassin copy() {
        return new SilumgarAssassin(this);
    }
}
