package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class DerangedAssistant extends CardImpl {

    public DerangedAssistant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}, Mill a card: Add {C}.
        ColorlessManaAbility ability = new ColorlessManaAbility();
        ability.addCost(new MillCardsCost());
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    private DerangedAssistant(final DerangedAssistant card) {
        super(card);
    }

    @Override
    public DerangedAssistant copy() {
        return new DerangedAssistant(this);
    }
}
