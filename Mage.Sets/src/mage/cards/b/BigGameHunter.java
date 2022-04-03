
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class BigGameHunter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public BigGameHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.HUMAN, SubType.REBEL, SubType.ASSASSIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Big Game Hunter enters the battlefield, destroy target creature with power 4 or greater. It can't be regenerated.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(true));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        // Madness {B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{B}")));
    }

    private BigGameHunter(final BigGameHunter card) {
        super(card);
    }

    @Override
    public BigGameHunter copy() {
        return new BigGameHunter(this);
    }
}
