
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureAllEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.FaceDownPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author LevelX2
 */
public final class Ixidron extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("face-down creatures on the battlefield");
    private static final FilterCreaturePermanent filterTurnFaceDown = new FilterCreaturePermanent("other nontoken creatures");

    static {
        filter.add(new FaceDownPredicate());
        filterTurnFaceDown.add(new AnotherPredicate());
        filterTurnFaceDown.add(Predicates.not(new TokenPredicate()));
    }

    public Ixidron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Ixidron enters the battlefield, turn all other nontoken creatures face down.
        this.addAbility(new EntersBattlefieldAbility(new BecomesFaceDownCreatureAllEffect(filterTurnFaceDown)));

        // Ixidron's power and toughness are each equal to the number of face-down creatures on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
    }

    public Ixidron(final Ixidron card) {
        super(card);
    }

    @Override
    public Ixidron copy() {
        return new Ixidron(this);
    }
}
