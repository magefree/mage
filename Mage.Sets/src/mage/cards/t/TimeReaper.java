package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.target.common.TargetCardInExile;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class TimeReaper extends CardImpl {

    private static final FilterCard filter = new FilterCard("face-up exiled card");

    static {
        filter.add(Predicates.not(FaceDownPredicate.instance));
    }

    public TimeReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Consume Anomaly -- Whenever Time Reaper deals combat damage to a player, put target face-up card they own in exile on the bottom of their library. If you do, you gain 3 life.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new PutOnLibraryTargetEffect(false), false, true);
        ability.addEffect(new GainLifeEffect(3)); //I don't think the move can fail? If there's no target then the trigger won't happen
        ability.addTarget(new TargetCardInExile(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        ability.withFlavorWord("Consume Anomaly");
        this.addAbility(ability);
    }

    private TimeReaper(final TimeReaper card) {
        super(card);
    }

    @Override
    public TimeReaper copy() {
        return new TimeReaper(this);
    }
}
