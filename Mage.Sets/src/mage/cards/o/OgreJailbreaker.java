package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class OgreJailbreaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Gate");

    static {
        filter.add(SubType.GATE.getPredicate());
    }

    public OgreJailbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Ogre Jailbreaker can attack as though it didn't have defender as long as you control a Gate.
        Effect effect = new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter));
        effect.setText("{this} can attack as though it didn't have defender as long as you control a Gate");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private OgreJailbreaker(final OgreJailbreaker card) {
        super(card);
    }

    @Override
    public OgreJailbreaker copy() {
        return new OgreJailbreaker(this);
    }
}
