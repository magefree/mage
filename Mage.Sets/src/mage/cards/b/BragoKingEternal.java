package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BragoKingEternal extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("nonland permanents you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public BragoKingEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT, SubType.NOBLE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Brago, King Eternal deals combat damage to a player, exile any number of target nonland permanents you control, then return those cards to the battlefield under their owner's control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ExileThenReturnTargetEffect(false, true), false);
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter, false));
        this.addAbility(ability);
    }

    private BragoKingEternal(final BragoKingEternal card) {
        super(card);
    }

    @Override
    public BragoKingEternal copy() {
        return new BragoKingEternal(this);
    }
}
