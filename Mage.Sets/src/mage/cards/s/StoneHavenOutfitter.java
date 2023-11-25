package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;

/**
 *
 * @author LevelX2
 */
public final class StoneHavenOutfitter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("equipped creatures you control");

    static {
        filter.add(EquippedPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public StoneHavenOutfitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Equipped creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        // Whenever an equipped creature you control dies, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new DrawCardSourceControllerEffect(1), false, filter)
                .setTriggerPhrase("Whenever an equipped creature you control dies, "));
    }

    private StoneHavenOutfitter(final StoneHavenOutfitter card) {
        super(card);
    }

    @Override
    public StoneHavenOutfitter copy() {
        return new StoneHavenOutfitter(this);
    }
}
