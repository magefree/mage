
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HalimarTidecaller extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Land creatures");
    private static final FilterCard filterCard = new FilterCard("card with awaken from your graveyard");

    static {
        filter.add(CardType.LAND.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        filterCard.add(new AbilityPredicate(AwakenAbility.class));
    }

    public HalimarTidecaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Halimar Tidecaller enters the battlefield, you may return target card with awaken from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filterCard));
        this.addAbility(ability);

        // Land creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private HalimarTidecaller(final HalimarTidecaller card) {
        super(card);
    }

    @Override
    public HalimarTidecaller copy() {
        return new HalimarTidecaller(this);
    }
}
