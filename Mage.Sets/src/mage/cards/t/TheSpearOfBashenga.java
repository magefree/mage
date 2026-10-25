package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonarchIsNotSetCondition;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsAttachedAttackingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class TheSpearOfBashenga extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("tapped nonland permanent that player controls");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(DefendingPlayerControlsAttachedAttackingPredicate.instance);
    }

    public TheSpearOfBashenga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // When The Spear of Bashenga enters, if there is no monarch, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect())
                .withInterveningIf(MonarchIsNotSetCondition.instance).addHint(MonarchHint.instance));

        // Equipped creature gets +2/+2 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has vigilance"));
        this.addAbility(ability);

        // Whenever equipped creature attacks the monarch, destroy target tapped nonland permanent that player controls.
        ability = new TheSpearOfBashengaTriggeredAbility();
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private TheSpearOfBashenga(final TheSpearOfBashenga card) {
        super(card);
    }

    @Override
    public TheSpearOfBashenga copy() {
        return new TheSpearOfBashenga(this);
    }
}

class TheSpearOfBashengaTriggeredAbility extends AttacksAttachedTriggeredAbility {

    TheSpearOfBashengaTriggeredAbility() {
        super(new DestroyTargetEffect());
        setTriggerPhrase("Whenever equipped creature attacks the monarch, ");
    }

    private TheSpearOfBashengaTriggeredAbility(final TheSpearOfBashengaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheSpearOfBashengaTriggeredAbility copy() {
        return new TheSpearOfBashengaTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(game.getMonarchId()) && super.checkTrigger(event, game);
    }
}
