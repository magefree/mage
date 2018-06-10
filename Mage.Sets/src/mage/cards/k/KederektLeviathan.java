
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author Plopman
 */
public final class KederektLeviathan extends CardImpl {
    
    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("all other nonland permanents");
    static{
        filter.add(new AnotherPredicate());
    }

    public KederektLeviathan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Kederekt Leviathan enters the battlefield, return all other nonland permanents to their owners' hands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandFromBattlefieldAllEffect(filter)));
        // Unearth {6}{U}
        this.addAbility(new UnearthAbility(new ManaCostsImpl("{6}{U}")));
    }

    public KederektLeviathan(final KederektLeviathan card) {
        super(card);
    }

    @Override
    public KederektLeviathan copy() {
        return new KederektLeviathan(this);
    }
}
