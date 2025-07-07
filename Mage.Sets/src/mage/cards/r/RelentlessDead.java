package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.DynamicValueGenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RelentlessDead extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("another target Zombie creature card with mana value X from your graveyard"); // This target defines X

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public RelentlessDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Relentless Dead dies, you may pay {B}. If you do, return it to its owner's hand.
        this.addAbility(new DiesSourceTriggeredAbility(new DoIfCostPaid(new ReturnToHandSourceEffect().setText("return it to its owner's hand"), new ManaCostsImpl<>("{B}"))));

        // When Relentless Dead dies, you may pay {X}. If you do, return another target Zombie creature card with converted mana cost X from your graveyard to the battlefield.
        Ability ability = new DiesSourceTriggeredAbility(new DoIfCostPaid(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new DynamicValueGenericManaCost(TargetManaValue.instance, "{X}")));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private RelentlessDead(final RelentlessDead card) {
        super(card);
    }

    @Override
    public RelentlessDead copy() {
        return new RelentlessDead(this);
    }
}
