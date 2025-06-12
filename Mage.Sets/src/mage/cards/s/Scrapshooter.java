package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Scrapshooter extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Scrapshooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Gift a card
        this.addAbility(new GiftAbility(this, GiftType.CARD));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Scrapshooter enters, if the gift was promised, destroy target artifact or enchantment an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect()).withInterveningIf(GiftWasPromisedCondition.TRUE);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private Scrapshooter(final Scrapshooter card) {
        super(card);
    }

    @Override
    public Scrapshooter copy() {
        return new Scrapshooter(this);
    }
}
