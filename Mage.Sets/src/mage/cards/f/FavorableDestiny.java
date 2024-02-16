
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EnchantedCreatureColorCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class FavorableDestiny extends CardImpl {

    public FavorableDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+2 as long as it's white.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostEnchantedEffect(1, 2),
                        new EnchantedCreatureColorCondition(ObjectColor.WHITE),
                        "Enchanted creature gets +1/+2 as long as it's white."
                )
        ));

        // Enchanted creature has shroud as long as its controller controls another creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilityAttachedEffect(
                                ShroudAbility.getInstance(),
                                AttachmentType.AURA
                        ),
                        new FavorableDestinyCondition(),
                        "Enchanted creature has shroud as long as its controller controls another creature."
                )
        ));
    }

    private FavorableDestiny(final FavorableDestiny card) {
        super(card);
    }

    @Override
    public FavorableDestiny copy() {
        return new FavorableDestiny(this);
    }
}

class FavorableDestinyCondition implements Condition {

    public FavorableDestinyCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature != null) {
                Player controller = game.getPlayer(creature.getControllerId());
                if (controller != null) {
                    for (Permanent perm : game.getBattlefield().getActivePermanents(controller.getId(), game)) {
                        if (perm.isControlledBy(controller.getId())
                                && perm.isCreature(game)
                                && !perm.equals(creature)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
