
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class RiptideChimera extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an enchantment you control");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public RiptideChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.CHIMERA);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your upkeep, return an enchantment you control to its owner's hand.
        Effect effect = new ReturnToHandChosenControlledPermanentEffect(filter, 1);
        effect.setText("return an enchantment you control to its owner's hand");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false));
    }

    private RiptideChimera(final RiptideChimera card) {
        super(card);
    }

    @Override
    public RiptideChimera copy() {
        return new RiptideChimera(this);
    }
}
