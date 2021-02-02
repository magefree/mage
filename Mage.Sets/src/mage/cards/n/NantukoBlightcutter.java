
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class NantukoBlightcutter extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public NantukoBlightcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        // Threshold - Nantuko Blightcutter gets +1/+1 for each black permanent your opponents control as long as seven or more cards are in your graveyard.
        PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(count, count, Duration.WhileOnBattlefield), new CardsInControllerGraveyardCondition(7),
            "{this} gets +1/+1 for each black permanent your opponents control as long as seven or more cards are in your graveyard"));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private NantukoBlightcutter(final NantukoBlightcutter card) {
        super(card);
    }

    @Override
    public NantukoBlightcutter copy() {
        return new NantukoBlightcutter(this);
    }
}
