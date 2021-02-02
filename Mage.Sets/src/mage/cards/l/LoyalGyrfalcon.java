
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class LoyalGyrfalcon extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("white spell");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(TargetController.YOU.getControllerPredicate());
    }
    
    private static final String rule = "Whenever you cast a white spell, {this} loses defender until end of turn.";

    public LoyalGyrfalcon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.BIRD);

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever you cast a white spell, Loyal Gyrfalcon loses defender until end of turn.
        this.addAbility(new SpellCastAllTriggeredAbility(new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.EndOfTurn), filter, false, rule));
        
    }

    private LoyalGyrfalcon(final LoyalGyrfalcon card) {
        super(card);
    }

    @Override
    public LoyalGyrfalcon copy() {
        return new LoyalGyrfalcon(this);
    }
}