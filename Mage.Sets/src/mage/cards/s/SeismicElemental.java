
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class SeismicElemental extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public SeismicElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Seismic Elemental enters the battlefield, creatures without flying can't block this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CantBlockAllEffect(filter, Duration.EndOfTurn), false));
    }

    private SeismicElemental(final SeismicElemental card) {
        super(card);
    }

    @Override
    public SeismicElemental copy() {
        return new SeismicElemental(this);
    }
}
