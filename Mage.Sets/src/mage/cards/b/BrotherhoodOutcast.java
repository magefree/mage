package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrotherhoodOutcast extends CardImpl {

    private static final FilterCard filter = new FilterCard("Aura or Equipment card with mana value 3 or less from your graveyard");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public BrotherhoodOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Brotherhood Outcast enters the battlefield, choose one --
        // * Return target Aura or Equipment card with mana value 3 or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));

        // * Put a shield counter on target creature.
        ability.addMode(new Mode(new AddCountersTargetEffect(CounterType.SHIELD.createInstance()))
                .addTarget(new TargetCreaturePermanent()));
        this.addAbility(ability);
    }

    private BrotherhoodOutcast(final BrotherhoodOutcast card) {
        super(card);
    }

    @Override
    public BrotherhoodOutcast copy() {
        return new BrotherhoodOutcast(this);
    }
}
