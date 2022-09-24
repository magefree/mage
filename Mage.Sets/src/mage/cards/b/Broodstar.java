
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Loki
 */
public final class Broodstar extends CardImpl {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public Broodstar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}{U}{U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
        this.addAbility(FlyingAbility.getInstance());
        // Broodstar's power and toughness are each equal to the number of artifacts you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
    }

    private Broodstar(final Broodstar card) {
        super(card);
    }

    @Override
    public Broodstar copy() {
        return new Broodstar(this);
    }
}
