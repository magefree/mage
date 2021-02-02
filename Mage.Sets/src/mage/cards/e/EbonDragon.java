
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class EbonDragon extends CardImpl {

    public EbonDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Ebon Dragon enters the battlefield, you may have target opponent discard a card.
        Effect effect = new DiscardTargetEffect(1, false);
        effect.setText("you may have target opponent discard a card");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private EbonDragon(final EbonDragon card) {
        super(card);
    }

    @Override
    public EbonDragon copy() {
        return new EbonDragon(this);
    }
}
