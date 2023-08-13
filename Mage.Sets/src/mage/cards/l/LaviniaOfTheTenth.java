

package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DetainAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author LevelX2
 */
public final class LaviniaOfTheTenth  extends CardImpl {

    private static final FilterPermanent filterDetain = new FilterPermanent("each nonland permanent your opponents control with mana value 4 or less");
    static {
        filterDetain.add(TargetController.OPPONENT.getControllerPredicate());
        filterDetain.add(Predicates.not(CardType.LAND.getPredicate()));
        filterDetain.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }

    public LaviniaOfTheTenth (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.supertype.add(SuperType.LEGENDARY);


        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        // When Lavinia of the Tenth enters the battlefield, detain each nonland permanent your opponents control with converted mana cost 4 or less.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DetainAllEffect(filterDetain)));

    }

    public LaviniaOfTheTenth (final LaviniaOfTheTenth card) {
        super(card);
    }

    @Override
    public LaviniaOfTheTenth copy() {
        return new LaviniaOfTheTenth(this);
    }

}
