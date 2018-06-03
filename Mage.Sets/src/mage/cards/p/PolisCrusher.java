
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FirstTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class PolisCrusher extends CardImpl {

    private static final FilterCard filter = new FilterCard("enchantments");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    private final UUID originalId;

    public PolisCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // protection from enchantments
        this.addAbility(new ProtectionAbility(filter));
        // {4}{R}{G}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{4}{R}{G}", 3));
        // Whenever Polis Crusher deals combat damage to a player, if Polis Crusher is monstrous, destroy target enchantment that player controls.
        Ability ability = new ConditionalTriggeredAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(new DestroyTargetEffect(), false, true),
                MonstrousCondition.instance,
                "Whenever {this} deals combat damage to a player, if {this} is monstrous, destroy target enchantment that player controls.");
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    public PolisCrusher(final PolisCrusher card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            for (Effect effect : ability.getEffects()) {
                if (effect instanceof DestroyTargetEffect) {
                    Player attackedPlayer = game.getPlayer(effect.getTargetPointer().getFirst(game, ability));
                    if (attackedPlayer != null) {
                        ability.getTargets().clear();
                        FilterPermanent filterEnchantment = new FilterEnchantmentPermanent("enchantment attacked player controls");
                        filterEnchantment.add(new ControllerIdPredicate(attackedPlayer.getId()));
                        Target target = new TargetPermanent(filterEnchantment);
                        ability.addTarget(target);
                        effect.setTargetPointer(new FirstTargetPointer());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public PolisCrusher copy() {
        return new PolisCrusher(this);
    }
}
