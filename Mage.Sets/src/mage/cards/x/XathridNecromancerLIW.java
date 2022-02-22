
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
import mage.game.permanent.token.IllusionWhiteToken;

/**
 *
 * @author mschatz
 */
public final class XathridNecromancerLIW extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Leviathan creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.LEVIATHAN.getPredicate());
    }

    public XathridNecromancerLIW(UUID ownerId, CardSetInfo setInfo) {
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

        // Whenever Xathrid Necromancer or another Leviathan creature you control dies, create a tapped 2/2 white Illusion creature token.
        Effect effect = new CreateTokenEffect(new IllusionWhiteToken(), 1, true, false);
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(effect, false, filter);
        this.addAbility(ability);

    }

    private XathridNecromancerLIW(final XathridNecromancerLIW card) {
        super(card);
    }

    @Override
    public XathridNecromancerLIW copy() {
        return new XathridNecromancerLIW(this);
    }
}
