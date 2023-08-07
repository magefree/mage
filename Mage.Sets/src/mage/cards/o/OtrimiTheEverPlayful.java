package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OtrimiTheEverPlayful extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mutate");

    static {
        filter.add(new AbilityPredicate(MutateAbility.class));
    }

    public OtrimiTheEverPlayful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Mutate {1}{B}{G}{U}
        this.addAbility(new MutateAbility(this, "{1}{B}{G}{U}"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature deals combat damage to a player, return target creature card with mutate from your graveyard to your hand.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), false
        ).setTriggerPhrase("Whenever this creature deals combat damage to a player, ");
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private OtrimiTheEverPlayful(final OtrimiTheEverPlayful card) {
        super(card);
    }

    @Override
    public OtrimiTheEverPlayful copy() {
        return new OtrimiTheEverPlayful(this);
    }
}
