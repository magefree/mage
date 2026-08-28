package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
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
        final SimpleActivatedAbility ability = new SimpleActivatedAbility(new BasicManaEffect(Mana.ColorlessMana(1)), new TapSourceCost());
        ability.addCost(new MillCardsCost());
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
