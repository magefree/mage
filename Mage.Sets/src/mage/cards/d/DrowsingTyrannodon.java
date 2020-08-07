package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrowsingTyrannodon extends CardImpl {

    public DrowsingTyrannodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // As long as you control a creature with power 4 or greater, Drowsing Tyrannodon can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(
                        Duration.WhileOnBattlefield
                ), FerociousCondition.instance
        ).setText("as long as you control a creature with power 4 or greater, " +
                "{this} can attack as though it didn't have defender")));
    }

    private DrowsingTyrannodon(final DrowsingTyrannodon card) {
        super(card);
    }

    @Override
    public DrowsingTyrannodon copy() {
        return new DrowsingTyrannodon(this);
    }
}
