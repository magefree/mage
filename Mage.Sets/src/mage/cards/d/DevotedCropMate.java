
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Styxo
 */
public final class DevotedCropMate extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public DevotedCropMate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // You may exert Devoted Crop-Mate as it attacks. When you do, return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("return target creature card with mana value 2 or less from your graveyard to the battlefield");
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(effect);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        addAbility(new ExertAbility(ability));

    }

    private DevotedCropMate(final DevotedCropMate card) {
        super(card);
    }

    @Override
    public DevotedCropMate copy() {
        return new DevotedCropMate(this);
    }
}
