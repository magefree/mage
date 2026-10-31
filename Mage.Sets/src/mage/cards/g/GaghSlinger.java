package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author muz
 */
public final class GaghSlinger extends CardImpl {

    private static final FilterCard spellFilter = new FilterCard("Klingon spells and Warrior spells");
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Klingon or Warrior you control");

    static {
        spellFilter.add(Predicates.or(SubType.KLINGON.getPredicate(), SubType.WARRIOR.getPredicate()));
        filter.add(Predicates.or(SubType.KLINGON.getPredicate(), SubType.WARRIOR.getPredicate()));
        filter.add(AnotherPredicate.instance);
    }

    public GaghSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Klingon spells and Warrior spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(spellFilter, 1)));

        // Whenever another Klingon or Warrior you control enters, this creature deals 1 damage to each opponent.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), filter));
    }

    private GaghSlinger(final GaghSlinger card) {
        super(card);
    }

    @Override
    public GaghSlinger copy() {
        return new GaghSlinger(this);
    }
}
