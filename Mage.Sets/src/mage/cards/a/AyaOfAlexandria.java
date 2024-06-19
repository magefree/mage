package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.game.permanent.token.AssassinMenaceToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AyaOfAlexandria extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a historic creature you control");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public AyaOfAlexandria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever a historic creature you control deals combat damage to a player, create a 1/1 black Assassin creature token with menace.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new CreateTokenEffect(new AssassinMenaceToken()), filter,
                false, SetTargetPointer.NONE, true
        ));
    }

    private AyaOfAlexandria(final AyaOfAlexandria card) {
        super(card);
    }

    @Override
    public AyaOfAlexandria copy() {
        return new AyaOfAlexandria(this);
    }
}
