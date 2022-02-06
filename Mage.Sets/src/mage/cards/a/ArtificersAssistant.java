
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.HistoricPredicate;

/**
 *
 * @author TheElk801
 */
public final class ArtificersAssistant extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a historic spell");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public ArtificersAssistant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a Historic spell scry 1.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ScryEffect(1, false), filter, false));
    }

    private ArtificersAssistant(final ArtificersAssistant card) {
        super(card);
    }

    @Override
    public ArtificersAssistant copy() {
        return new ArtificersAssistant(this);
    }
}
