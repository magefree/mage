
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class AbominationOfGudul extends CardImpl {

    public AbominationOfGudul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{G}{U}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Abomination of Gudul deals combat damage to a player, you may draw a card. If you do, discard a card.
        Effect effect = new DrawDiscardControllerEffect(1,1);
        effect.setText("you may draw a card. If you do, discard a card");
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(effect, true));
        
        // Morph 2BGU
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{B}{G}{U}")));
    }

    private AbominationOfGudul(final AbominationOfGudul card) {
        super(card);
    }

    @Override
    public AbominationOfGudul copy() {
        return new AbominationOfGudul(this);
    }
}
