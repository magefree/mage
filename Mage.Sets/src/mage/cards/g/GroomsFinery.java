package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEquipmentPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GroomsFinery extends CardImpl {

    private static final FilterPermanent filter = new FilterEquipmentPermanent();

    static {
        filter.add(new NamePredicate("Bride's Gown"));
        filter.add(GroomsFineryPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, false);
    private static final Hint hint = new ConditionHint(
            condition, "An Equipment named Bride's Gown is attached to a creature you control"
    );

    public GroomsFinery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0. It gets an additional +0/+2 and has deathtouch as long as an Equipment named Bride's Gown is attached to a creature you control.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new ConditionalContinuousEffect(
                new BoostEquippedEffect(0, 2),
                condition, "It gets an additional +0/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(
                        DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT
                ), condition, "and has deathtouch as long as an Equipment " +
                "named Bride's Gown is attached to a creature you control"
        ));
        this.addAbility(ability.addHint(hint));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private GroomsFinery(final GroomsFinery card) {
        super(card);
    }

    @Override
    public GroomsFinery copy() {
        return new GroomsFinery(this);
    }
}

enum GroomsFineryPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = game.getPermanent(input.getObject().getAttachedTo());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(input.getPlayerId());
    }
}
