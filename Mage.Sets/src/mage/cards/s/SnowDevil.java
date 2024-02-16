package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class SnowDevil extends CardImpl {

    public SnowDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield)));

        // Enchanted creature has first strike as long as it's blocking and you control a snow land.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilityAttachedEffect(
                                FirstStrikeAbility.getInstance(),
                                AttachmentType.AURA,
                                Duration.WhileOnBattlefield),
                        SnowDevilCondition.instance,
                        "Enchanted creature has first strike as long as it's blocking and you control a snow land"
                )));
    }

    private SnowDevil(final SnowDevil card) {
        super(card);
    }

    @Override
    public SnowDevil copy() {
        return new SnowDevil(this);
    }
}

enum SnowDevilCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        FilterLandPermanent filter = new FilterLandPermanent();
        filter.add(SuperType.SNOW.getPredicate());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Player controller = game.getPlayer(enchantment.getControllerId());
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature != null
                    && controller != null) {
                CombatGroup combatGroup = game.getCombat().findGroupOfBlocker(creature.getId());
                if (combatGroup != null
                        && !game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
