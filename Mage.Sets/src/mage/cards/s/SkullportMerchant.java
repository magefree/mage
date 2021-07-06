package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class SkullportMerchant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another creature or a Treasure");

    static {
        Predicate anotherCreaturePredicate = Predicates.and(CardType.CREATURE.getPredicate(), AnotherPredicate.instance);
        filter.add(Predicates.or(anotherCreaturePredicate, SubType.TREASURE.getPredicate()));
    }

    public SkullportMerchant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Skullport Merchant enters the battlefield, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // {1}{B}, Sacrifice another creature or a Treasure: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private SkullportMerchant(final SkullportMerchant card) {
        super(card);
    }

    @Override
    public SkullportMerchant copy() {
        return new SkullportMerchant(this);
    }
}
