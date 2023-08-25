
package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GoblinElectromancer extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("Instant and sorcery spells");

    public GoblinElectromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private GoblinElectromancer(final GoblinElectromancer card) {
        super(card);
    }

    @Override
    public GoblinElectromancer copy() {
        return new GoblinElectromancer(this);
    }
}
