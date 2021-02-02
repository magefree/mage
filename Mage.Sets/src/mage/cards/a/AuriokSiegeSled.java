
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByTargetSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Galatolol
 */
public final class AuriokSiegeSled extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creature");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public AuriokSiegeSled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {1}: Target artifact creature blocks Auriok Siege Sled this turn if able.
        MustBeBlockedByTargetSourceEffect effect = new MustBeBlockedByTargetSourceEffect(Duration.EndOfTurn);
        effect.setText("Target artifact creature blocks {this} this turn if able.");
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{1}"));
        ability1.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability1);

        // {1}: Target artifact creature can't block Auriok Siege Sled this turn.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedByTargetSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl("{1}"));
        ability2.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability2);
    }

    private AuriokSiegeSled(final AuriokSiegeSled card) {
        super(card);
    }

    @Override
    public AuriokSiegeSled copy() {
        return new AuriokSiegeSled(this);
    }
}
