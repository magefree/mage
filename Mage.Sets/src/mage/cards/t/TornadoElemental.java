
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author emerald000
 */
public final class TornadoElemental extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public TornadoElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Tornado Elemental enters the battlefield, it deals 6 damage to each creature with flying.
        Effect effect = new DamageAllEffect(6, filter);
        effect.setText("it deals 6 damage to each creature with flying");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));
        
        // You may have Tornado Elemental assign its combat damage as though it weren't blocked.
        this.addAbility(DamageAsThoughNotBlockedAbility.getInstance());
    }

    private TornadoElemental(final TornadoElemental card) {
        super(card);
    }

    @Override
    public TornadoElemental copy() {
        return new TornadoElemental(this);
    }
}
