package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetCardInYourGraveyard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RubbleRouser extends CardImpl {

    public RubbleRouser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When this creature enters, you may discard a card. If you do, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost())
        ));

        // {T}, Exile a card from your graveyard: Add {R}. When you do, this creature deals 1 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(new BasicManaEffect(Mana.RedMana(1)), new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard()));
        ability.addEffect(new DamagePlayersEffect(1, TargetController.OPPONENT));
        this.addAbility(ability);
    }

    private RubbleRouser(final RubbleRouser card) {
        super(card);
    }

    @Override
    public RubbleRouser copy() {
        return new RubbleRouser(this);
    }
}
