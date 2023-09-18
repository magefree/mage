
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.EldraziScionToken;

/**
 *
 * @author fireshoes
 */
public final class SifterOfSkulls extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken creature you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SifterOfSkulls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        
        // Whenever another nontoken creature you control dies, create a 1/1 colorless Eldrazi Scion creature token.
        // It has "Sacrifice this creature: Add {C}."
        Effect effect = new CreateTokenEffect(new EldraziScionToken());
        effect.setText("create a 1/1 colorless Eldrazi Scion creature token. It has \"Sacrifice this creature: Add {C}.\"");
        this.addAbility(new DiesCreatureTriggeredAbility(effect, false, filter));
    }

    private SifterOfSkulls(final SifterOfSkulls card) {
        super(card);
    }

    @Override
    public SifterOfSkulls copy() {
        return new SifterOfSkulls(this);
    }
}
