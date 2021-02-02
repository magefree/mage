
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class KabiraEvangel extends CardImpl {

    private static final FilterControlledCreaturePermanent FILTER1 = new FilterControlledCreaturePermanent();

    static {
        FILTER1.add(SubType.ALLY.getPredicate());
    }

    public KabiraEvangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        FilterPermanent filter2 = new FilterPermanent(getName() + " or another Ally");
        filter2.add(Predicates.or(new CardIdPredicate(this.getId()), SubType.ALLY.getPredicate()));

        // Whenever Kabira Evangel or another Ally enters the battlefield under your control, you may choose a color. If you do, Allies you control gain protection from the chosen color until end of turn.
        Effect effect = new GainProtectionFromColorAllEffect(Duration.EndOfTurn, FILTER1);
        effect.setText("choose a color. If you do, Allies you control gain protection from the chosen color until end of turn.");
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, effect, filter2, true));
    }

    private KabiraEvangel(final KabiraEvangel card) {
        super(card);
    }

    @Override
    public KabiraEvangel copy() {
        return new KabiraEvangel(this);
    }
}
