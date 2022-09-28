
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author anonymous
 */
public final class AnimalBoneyard extends CardImpl {

    public AnimalBoneyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted land has "{T}, Sacrifice a creature: You gain life equal to that creature's toughness."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AnimalBoneyardEffect(), new TapSourceCost());
        gainedAbility.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA, Duration.WhileOnBattlefield,
                "Enchanted land has \"{T}, Sacrifice a creature: You gain life equal to that creature's toughness.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private AnimalBoneyard(final AnimalBoneyard card) {
        super(card);
    }

    @Override
    public AnimalBoneyard copy() {
        return new AnimalBoneyard(this);
    }
}

class AnimalBoneyardEffect extends OneShotEffect {

    public AnimalBoneyardEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to that creature's toughness";
    }

    public AnimalBoneyardEffect(final AnimalBoneyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {

            int toughness = 0;

            for (Cost cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                    toughness = ((SacrificeTargetCost) cost).getPermanents().get(0).getToughness().getValue();
                    break;
                }
            }
            if (toughness > 0) {
                controller.gainLife(toughness, game, source);
            }
            return true;
        }
        return false;

    }

    @Override
    public AnimalBoneyardEffect copy() {
        return new AnimalBoneyardEffect(this);
    }
}
