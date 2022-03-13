package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author weirddan455
 */
public final class HalvarGodOfBattle extends ModalDoubleFacesCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterPermanent("aura or equipment attached to a creature you control");

    static {
        filter.add(Predicates.or(EnchantedPredicate.instance, EquippedPredicate.instance));
        filter2.add(Predicates.or(SubType.AURA.getPredicate(), SubType.EQUIPMENT.getPredicate()));
        filter2.add(new HalvarGodOfBattlePredicate(StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    public HalvarGodOfBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{2}{W}{W}",
                "Sword of the Realms", new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "{1}{W}"
        );

        // 1.
        // Halvar, God of Battle
        // Legendary Creature - God
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(new MageInt(4), new MageInt(4));

        // Creatures you control that are enchanted or equipped have double strike.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter
                ).setText("Creatures you control that are enchanted or equipped have double strike")
        ));

        // At the beginning of each combat, you may attach target Aura or Equipment attached to a creature you control to target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new HalvarGodOfBattleEffect(), TargetController.ANY, false);
        ability.addTarget(new TargetPermanent(filter2));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // Sword of the Realms
        // Legendary Artifact - Equipment
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);

        // Equipped creature gets +2/+0 and has vigilance
        ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has vigilance"));
        this.getRightHalfCard().addAbility(ability);

        // Whenever equipped creature dies, return it to its owners hand
        this.getRightHalfCard().addAbility(new DiesAttachedTriggeredAbility(
                new SwordOfTheRealmsEffect(), "equipped creature"
        ));

        // Equip {1}{W}
        this.getRightHalfCard().addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{1}{W}")));
    }

    private HalvarGodOfBattle(final HalvarGodOfBattle card) {
        super(card);
    }

    @Override
    public HalvarGodOfBattle copy() {
        return new HalvarGodOfBattle(this);
    }
}

class HalvarGodOfBattleEffect extends OneShotEffect {

    public HalvarGodOfBattleEffect() {
        super(Outcome.BoostCreature);
        staticText = "you may attach target Aura or Equipment attached to a creature you control to target creature you control";
    }

    private HalvarGodOfBattleEffect(final HalvarGodOfBattleEffect effect) {
        super(effect);
    }

    @Override
    public HalvarGodOfBattleEffect copy() {
        return new HalvarGodOfBattleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent attachment = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (controller != null && attachment != null && creature != null && creature.isControlledBy(controller.getId())) {
            Permanent oldCreature = game.getPermanent(attachment.getAttachedTo());
            if (oldCreature != null && oldCreature.isControlledBy(controller.getId()) && !oldCreature.equals(creature)) {
                boolean canAttach = true;
                if (attachment.hasSubtype(SubType.AURA, game)) {
                    Target auraTarget = attachment.getSpellAbility().getTargets().get(0);
                    if (!auraTarget.canTarget(creature.getId(), game)) {
                        canAttach = false;
                    }
                }
                if (!canAttach) {
                    game.informPlayers(attachment.getLogName() + " was not attached to " + creature.getLogName()
                            + " because it's not a legal target for the aura");
                } else if (controller.chooseUse(Outcome.BoostCreature, "Attach " + attachment.getLogName()
                        + " to " + creature.getLogName() + "?", source, game)) {
                    oldCreature.removeAttachment(attachment.getId(), source, game);
                    creature.addAttachment(attachment.getId(), source, game);
                    game.informPlayers(attachment.getLogName() + " was unattached from " + oldCreature.getLogName()
                            + " and attached to " + creature.getLogName());
                    return true;
                }
            }
        }
        return false;
    }
}

class SwordOfTheRealmsEffect extends OneShotEffect {

    public SwordOfTheRealmsEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return it to its owner's hand";
    }

    private SwordOfTheRealmsEffect(final SwordOfTheRealmsEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfTheRealmsEffect copy() {
        return new SwordOfTheRealmsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card creature = (Card) getValue("attachedTo");
        if (controller == null || creature == null) {
            return false;
        }
        creature = game.getCard(creature.getId());
        return creature != null
                && Objects.equals(creature.getZoneChangeCounter(game), getValue("zcc"))
                && controller.moveCards(creature, Zone.HAND, source, game);
    }
}

class HalvarGodOfBattlePredicate implements ObjectSourcePlayerPredicate<Permanent> {

    private final FilterPermanent filter;

    public HalvarGodOfBattlePredicate(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        UUID attachedTo = input.getObject().getAttachedTo();
        Permanent permanent = game.getPermanent(attachedTo);
        return permanent != null && filter.match(permanent, input.getPlayerId(), input.getSource(), game);
    }

    @Override
    public String toString() {
        return "attached to " + filter.getMessage();
    }
}
