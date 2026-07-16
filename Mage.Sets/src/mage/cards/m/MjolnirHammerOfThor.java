package mage.cards.m;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.util.CardUtil;

/**
 *
 * @author nandmp
 */
public final class MjolnirHammerOfThor extends CardImpl {

    private static final FilterPermanent equipWorthyFilter = new FilterControlledCreaturePermanent("worthy");

    static {
        equipWorthyFilter.add(SuperType.LEGENDARY.getPredicate());
        equipWorthyFilter.add(Predicates.not(SubType.VILLAIN.getPredicate()));
        equipWorthyFilter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.WHITE)));
    }

    public MjolnirHammerOfThor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // When Mjolnir enters, it deals 4 damage to up to one target creature.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(4, "it"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Double all damage equipped creature would deal.
        this.addAbility(new SimpleStaticAbility(new MjolnirHammerOfThorEffect()));

        // Equip worthy {1}
        EquipAbility equipAbility = new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), new TargetPermanent(equipWorthyFilter), true);
        equipAbility.setReminderText("A creature is worthy if it's a legendary non-Villain that's red and/or white.");
        this.addAbility(equipAbility);

        // {2}{R}, Discard this card: It deals 2 damage to each creature.
        Ability channelAbility = new ChannelAbility("{2}{R}", new DamageAllEffect(2, "It", new FilterCreaturePermanent()));
        this.addAbility(channelAbility);
    }

    private MjolnirHammerOfThor(final MjolnirHammerOfThor card) {
        super(card);
    }

    @Override
    public MjolnirHammerOfThor copy() {
        return new MjolnirHammerOfThor(this);
    }
}

class MjolnirHammerOfThorEffect extends ReplacementEffectImpl {

    MjolnirHammerOfThorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "Double all damage equipped creature would deal";
    }

    private MjolnirHammerOfThorEffect(final MjolnirHammerOfThorEffect effect) {
        super(effect);
    }

    @Override
    public MjolnirHammerOfThorEffect copy() {
        return new MjolnirHammerOfThorEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        return equipment != null
                && equipment.getAttachedTo() != null
                && event.getSourceId().equals(equipment.getAttachedTo());
    }
}
