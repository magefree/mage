package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class LilianaTheFaultless extends CardImpl {

    private static final FilterPermanent filter
        = new FilterCreatureOrPlaneswalkerPermanent("another creature or planeswalker you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public LilianaTheFaultless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature or planeswalker you control enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
            new GainLifeEffect(1), filter
        ));

        // {1}, {T}, Discard a card: Another target creature or planeswalker you control gains hexproof until end of turn.
        Ability ability = new SimpleActivatedAbility(
            new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn)
                .setText("Another target creature or planeswalker you control gains hexproof until end of turn"),
            new ManaCostsImpl<>("{1}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private LilianaTheFaultless(final LilianaTheFaultless card) {
        super(card);
    }

    @Override
    public LilianaTheFaultless copy() {
        return new LilianaTheFaultless(this);
    }
}
