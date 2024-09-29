
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class DiligentExcavator extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a historic spell");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public DiligentExcavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a historic spell, target player puts the top two cards of their library into their graveyard.
        Ability ability = new SpellCastControllerTriggeredAbility(new MillCardsTargetEffect(2), filter, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DiligentExcavator(final DiligentExcavator card) {
        super(card);
    }

    @Override
    public DiligentExcavator copy() {
        return new DiligentExcavator(this);
    }
}
