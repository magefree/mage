
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.EldraziScionToken;

/**
 *
 * @author fireshoes
 */
public final class CatacombSifter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you control");
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }        

    public CatacombSifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{G}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        
        // When Catacomb Sifter enters the battlefield, create a 1/1 colorless Eldrazi Scion creature token. It has "Sacrifice this creature: Add {C}."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new EldraziScionToken())));
        
        // Whenever another creature you control dies, scry 1
        this.addAbility(new DiesCreatureTriggeredAbility(new ScryEffect(1), false, filter));
    }

    private CatacombSifter(final CatacombSifter card) {
        super(card);
    }

    @Override
    public CatacombSifter copy() {
        return new CatacombSifter(this);
    }
}
