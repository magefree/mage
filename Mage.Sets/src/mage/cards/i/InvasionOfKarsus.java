package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfKarsus extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature and each planeswalker");

    public InvasionOfKarsus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{2}{R}{R}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);
        this.secondSideCardClazz = mage.cards.r.RefractionElemental.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Karsus enters the battlefield, it deals 3 damage to each creature and each planeswalker.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DamageAllEffect(3, "it", filter)
        ));
    }

    private InvasionOfKarsus(final InvasionOfKarsus card) {
        super(card);
    }

    @Override
    public InvasionOfKarsus copy() {
        return new InvasionOfKarsus(this);
    }
}
