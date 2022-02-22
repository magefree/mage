
package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.FaerieGreenToken;

/**
 *
 * @author mschatz
 */
public final class XathridNecromancerKFG extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Kavu creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.KAVU.getPredicate());
    }

    public XathridNecromancerKFG(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        // Absorb initial Infest and Dread of Night Black
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Apply Prismatic Lace
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.color.setRed(true);
        this.color.setWhite(true);

        // Whenever Xathrid Necromancer or another Kavu creature you control dies, create a tapped 2/2 green Faerie creature token.
        Effect effect = new CreateTokenEffect(new FaerieGreenToken(), 1, true, false);
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(effect, false, filter);
        this.addAbility(ability);

    }

    private XathridNecromancerKFG(final XathridNecromancerKFG card) {
        super(card);
    }

    @Override
    public XathridNecromancerKFG copy() {
        return new XathridNecromancerKFG(this);
    }
}
