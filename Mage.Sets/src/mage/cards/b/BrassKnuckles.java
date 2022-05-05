package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author TheElk801
 */
public final class BrassKnuckles extends CardImpl {

    public BrassKnuckles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.EQUIPMENT);

        // When you cast this spell, copy it.
        this.addAbility(new CastSourceTriggeredAbility(new CopySourceSpellEffect().setText("copy it")));

        // Equipped creature has double strike as long as two or more Equipment are attached to it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(
                        DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
                ), BrassKnucklesCondition.instance, "equipped creature has double strike " +
                "as long as two or more Equipment are attached to it"
        )));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private BrassKnuckles(final BrassKnuckles card) {
        super(card);
    }

    @Override
    public BrassKnuckles copy() {
        return new BrassKnuckles(this);
    }
}

enum BrassKnucklesCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .of(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(Permanent::getAttachments)
                .map(Collection::stream)
                .map(stream -> stream.map(game::getPermanent))
                .map(stream -> stream.filter(Objects::nonNull))
                .map(stream -> stream.filter(p -> p.hasSubtype(SubType.EQUIPMENT, game)))
                .map(Stream::count)
                .map(x -> x >= 2)
                .orElse(false);
    }
}
