
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.HistoricPredicate;

/**
 *
 * @author TheElk801
 */
public final class MishrasSelfReplicator extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a historic spell");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public MishrasSelfReplicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a historic spell, you may pay {1}. If you do, create a token that's a copy of Mishra's Self-Replicator.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new CreateTokenCopySourceEffect()
                        .setText("create a token that's a copy of {this}. <i>(Artifacts, legendaries, and Sagas are historic.)</i>"),
                new ManaCostsImpl<>("{1}")), filter, false));

    }

    private MishrasSelfReplicator(final MishrasSelfReplicator card) {
        super(card);
    }

    @Override
    public MishrasSelfReplicator copy() {
        return new MishrasSelfReplicator(this);
    }
}
