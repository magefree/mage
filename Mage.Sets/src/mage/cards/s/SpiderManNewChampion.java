package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAnyTarget;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.SavedDiscardValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DiscardOneOrMoreCardsTriggeredAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SpiderManNewChampion extends CardImpl {

    public SpiderManNewChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you discard one or more cards, Spider-Man deals that much damage to any target.
        Ability ability = new DiscardOneOrMoreCardsTriggeredAbility(
            new DamageTargetEffect(SavedDiscardValue.MUCH)
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SpiderManNewChampion(final SpiderManNewChampion card) {
        super(card);
    }

    @Override
    public SpiderManNewChampion copy() {
        return new SpiderManNewChampion(this);
    }
}
