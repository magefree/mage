package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author halljared
 */
public final class NeglectedHeirloom extends TransformingDoubleFacedCard {

    public NeglectedHeirloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "{1}",
                "Ashmouth Blade",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, ""
        );

        // Equipped creature gets +1/+1.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // When equipped creature transforms, transform Neglected Heirloom.
        this.getLeftHalfCard().addAbility(new NeglectedHeirloomTriggeredAbility());

        // Equip {1}
        this.getLeftHalfCard().addAbility(new EquipAbility(1, false));

        // Ashmouth Blade

        // Equipped creature gets +3/+3 and has first strike.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 3));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has first strike"));
        this.getRightHalfCard().addAbility(ability);

        // Equip {3}
        this.getRightHalfCard().addAbility(new EquipAbility(3, false));
    }

    private NeglectedHeirloom(final NeglectedHeirloom card) {
        super(card);
    }

    @Override
    public NeglectedHeirloom copy() {
        return new NeglectedHeirloom(this);
    }

}

class NeglectedHeirloomTriggeredAbility extends TriggeredAbilityImpl {

    public NeglectedHeirloomTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect(), false);
        setTriggerPhrase("When equipped creature transforms, ");
    }

    private NeglectedHeirloomTriggeredAbility(final NeglectedHeirloomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return Optional
                .ofNullable(getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .filter(event.getTargetId()::equals)
                .isPresent();
    }

    @Override
    public NeglectedHeirloomTriggeredAbility copy() {
        return new NeglectedHeirloomTriggeredAbility(this);
    }
}
