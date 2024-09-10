package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class AshenmoorLiege extends CardImpl {

    private static final FilterCreaturePermanent filterBlackCreature = new FilterCreaturePermanent("black creatures");
    private static final FilterCreaturePermanent filterRedCreature = new FilterCreaturePermanent("red creatures");

    static {
        filterBlackCreature.add(new ColorPredicate(ObjectColor.BLACK));
        filterRedCreature.add(new ColorPredicate(ObjectColor.RED));
    }

    public AshenmoorLiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B/R}{B/R}{B/R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Other black creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterBlackCreature, true)));
        // Other red creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterRedCreature, true)));

        // Whenever Ashenmoor Liege becomes the target of a spell or ability an opponent controls, that player loses 4 life.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(new LoseLifeTargetEffect(4),
                StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.PLAYER, false));
    }

    private AshenmoorLiege(final AshenmoorLiege card) {
        super(card);
    }

    @Override
    public AshenmoorLiege copy() {
        return new AshenmoorLiege(this);
    }
}
