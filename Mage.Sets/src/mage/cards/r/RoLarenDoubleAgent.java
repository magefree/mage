package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author muz
 */
public final class RoLarenDoubleAgent extends CardImpl {

    public RoLarenDoubleAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BAJORAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Seek out -- Whenever you draw your second card each turn, Ro Laren deals 2 damage to each opponent.
        this.addAbility(new DrawNthCardTriggeredAbility(new DamagePlayersEffect(2, TargetController.OPPONENT)).withFlavorWord("Seek out"));

        // {1}, {T}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private RoLarenDoubleAgent(final RoLarenDoubleAgent card) {
        super(card);
    }

    @Override
    public RoLarenDoubleAgent copy() {
        return new RoLarenDoubleAgent(this);
    }
}
